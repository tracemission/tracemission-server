package org.wirvsvirushackathon.servcie;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Values;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.async.ResultCursor;
import org.wirvsvirushackathon.model.Person;
import org.wirvsvirushackathon.persistence.PersonQuery;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CompletionStage;


@Singleton
public class PersonService {

    @Inject
    private Driver driver;

    @Inject
    private SMSService smsService;

    public CompletionStage<Person> registerPerson(Person person, boolean verify) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(PersonQuery.CREATE_QUERY, PersonQuery.getParameterMap(person))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Person.from(record.get("p").asNode()))
                .thenCompose(persistedPerson -> session.closeAsync().thenApply(signal -> persistedPerson)).thenApply( persistedPerson -> {
                    if (verify) sendVerificationMessage(persistedPerson);
                    return person;
                });
    }

    public CompletionStage<Person> verifyPerson(Person person) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                    .runAsync(PersonQuery.VERIFY_QUERY, Values.parameters(Person.ID_PROP, person.getId(), Person.VERIFIED_PROP, true))
                    .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Person.from(record.get("p").asNode()))
                .thenCompose(persistedPerson -> session.closeAsync().thenApply(signal -> persistedPerson));
    }

    public CompletionStage<Person> checkVerification(Person person) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(PersonQuery.VERIFY_QUERY, Values.parameters(Person.ID_PROP, person.getId(), Person.VERIFIED_PROP, true))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Person.from(record.get("p").asNode()))
                .thenCompose(persistedPerson -> session.closeAsync().thenApply(signal -> persistedPerson));
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

    private String sendVerificationMessage(Person person){
        String key = null;
        try {
            key = generateKey();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        smsService.sendMessage("Herzlich Willkommen bei Tracemission " + person.getFirstName() + ".\n" +
                "Wir freuen uns sehr, dass du uns dabei unterstützen möchtest die Ausbreitung von Corona weitestgehend zu verhindern.\n" +
                "Dein Aktivierungscode lautet: "+key+"\n" +
                "Viele Grüße\n" +
                "Dein Tracemission Team\n", person.getPhone());
        return key;
    }

    private String generateKey() throws NoSuchAlgorithmException {
        SecretKey key = KeyGenerator.getInstance("AES").generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

}
