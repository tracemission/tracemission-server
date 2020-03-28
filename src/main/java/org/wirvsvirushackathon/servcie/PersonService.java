package org.wirvsvirushackathon.servcie;

import io.quarkus.security.AuthenticationFailedException;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Values;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.async.ResultCursor;
import org.wirvsvirushackathon.configuration.security.TokenGenerator;
import org.wirvsvirushackathon.model.Person;
import org.wirvsvirushackathon.model.Role;
import org.wirvsvirushackathon.persistence.PersonQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.CompletionStage;


@Singleton
public class PersonService {

    @Inject
    private Driver driver;

    @Inject
    private SMSService smsService;

    @Inject
    private TokenGenerator tokenGenerator;

    private Map<UUID, Long> verificationKey = new HashMap<>();

    public CompletionStage<Person> reportInfected(UUID id){
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(PersonQuery.REPORT_INFECTED_QUERY, Values.parameters(Person.ID_PROP, id.toString(), Person.INFECTED_PROP, true))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Person.from(record.get("p").asNode()))
                .thenCompose(persistedPerson -> session.closeAsync().thenApply(signal -> persistedPerson));
    }

    public CompletionStage<Person> registerPerson(Person person, boolean verify) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(PersonQuery.CREATE_QUERY, PersonQuery.getParameterMap(person))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Person.from(record.get("p").asNode()))
                .thenCompose(persistedPerson -> session.closeAsync().thenApply(signal -> persistedPerson)).thenApply(persistedPerson -> {
                    if (verify) {
                        long key = sendVerificationMessage(persistedPerson);
                        verificationKey.put(person.getId(), key);
                    }
                    return person;
                });
    }

    public CompletionStage<Person> verifyPerson(UUID id) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(PersonQuery.SELECT_ID_QUERY, Values.parameters(Person.ID_PROP, id.toString()))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Person.from(record.get("p").asNode()))
                .thenCompose(persistedPerson -> session.closeAsync().thenApply(signal -> {
                    long key = sendVerificationMessage(persistedPerson);
                    verificationKey.put(id, key);
                    return persistedPerson;
                }));
    }

    public CompletionStage<String> checkVerification(UUID id, long key) {
        boolean validVerification = key == verificationKey.getOrDefault(id, 1234L);
        if (validVerification) {
            AsyncSession session = driver.asyncSession();
            return session
                    .writeTransactionAsync(tx -> tx
                            .runAsync(PersonQuery.VERIFY_QUERY, PersonQuery.getVerifyParameterMap(id))
                            .thenCompose(ResultCursor::singleAsync)
                    )
                    .thenApply(record -> Person.from(record.get("p").asNode()))
                    .thenCompose(persistedPerson -> session.closeAsync().thenApply(signal -> {
                        try {
                            return tokenGenerator.generateToken(persistedPerson.getId(), Role.PERSON);
                        } catch (Exception e) {
                            return null;
                        }
                    }));
        } else {
            throw new AuthenticationFailedException("Wrong Key");
        }
    }

    public CompletionStage<Person> getPersonById(UUID id) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(PersonQuery.SELECT_ID_QUERY, Values.parameters(Person.ID_PROP, id.toString()))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Person.from(record.get("p").asNode()))
                .thenCompose(persistedPerson -> session.closeAsync().thenApply(signal -> persistedPerson));
    }

    private long sendVerificationMessage(Person person) {
        long key = generateKey();
        smsService.sendMessage("Herzlich Willkommen bei Tracemission " + person.getFirstName() + ".\n" +
                "Wir freuen uns sehr, dass du uns dabei unterstützen möchtest die Ausbreitung von Corona weitestgehend zu verhindern.\n" +
                "Dein Aktivierungscode lautet: " + key + "\n" +
                "Viele Grüße\n" +
                "Dein Tracemission Team\n", person.getPhone());
        return key;
    }

    private long generateKey() {
        long leftLimit = 1000;
        long rightLimit = 9999;
        return leftLimit + (long) (new Random().nextFloat() * (rightLimit - leftLimit));
    }

}
