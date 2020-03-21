package org.wirvsvirushackathon.servcie;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Values;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.async.ResultCursor;
import org.wirvsvirushackathon.model.Store;
import org.wirvsvirushackathon.persistence.StoreQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Singleton
public class StoreService {

    @Inject
    Driver driver;

    public CompletionStage<Store> registerStore(Store store) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(StoreQuery.CREATE_QUERY, StoreQuery.getParameterMap(store))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Store.from(record.get("s").asNode()))
                .thenCompose(persistedStore -> session.closeAsync().thenApply(signal -> persistedStore));
    }

    public CompletionStage<Store> getStoreById(UUID id) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(StoreQuery.SELECT_ID_QUERY, Values.parameters(Store.ID_PROP, id.toString()))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Store.from(record.get("s").asNode()))
                .thenCompose(persistedStore -> session.closeAsync().thenApply(signal -> persistedStore));
    }

}
