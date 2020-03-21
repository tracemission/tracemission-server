package org.wirvsvirushackathon.servcie;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Values;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.async.ResultCursor;
import org.wirvsvirushackathon.model.Person;
import org.wirvsvirushackathon.persistence.PersonQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletionStage;


@Singleton
public class PersonService {

    @Inject
    Driver driver;

    public CompletionStage<Person> registerPerson(Person person) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(PersonQuery.CREATE_QUERY, PersonQuery.getParameterMap(person))
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

}
