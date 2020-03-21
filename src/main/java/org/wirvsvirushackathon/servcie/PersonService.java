package org.wirvsvirushackathon.servcie;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Values;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.async.ResultCursor;
import org.wirvsvirushackathon.model.Person;
import org.wirvsvirushackathon.persistence.PersonQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
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
                    if (verify) {
                        smsService.sendMessage("Herzlich Willkommen bei Tracemission " + persistedPerson.getFirstName() + ".\n" +
                                "Wir freuen uns sehr, dass du uns dabei unterstützen möchtest die Ausbreitung von Corona weitestgehend zu verhindern.\n" +
                                "Dein Aktivierungscode lautet: 122345\n" +
                                "Viele Grüße\n" +
                                "Dein Tracemission Team\n", persistedPerson.getPhone());
                    }
                    return person;
                });
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

}
