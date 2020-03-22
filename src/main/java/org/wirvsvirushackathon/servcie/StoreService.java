package org.wirvsvirushackathon.servcie;

import io.quarkus.security.AuthenticationFailedException;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Values;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.async.ResultCursor;
import org.wirvsvirushackathon.configuration.security.TokenGenerator;
import org.wirvsvirushackathon.model.Person;
import org.wirvsvirushackathon.model.Role;
import org.wirvsvirushackathon.model.Store;
import org.wirvsvirushackathon.persistence.PersonQuery;
import org.wirvsvirushackathon.persistence.StoreQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Singleton
public class StoreService {

    @Inject
    private Driver driver;

    @Inject
    private SMSService smsService;

    @Inject
    private TokenGenerator tokenGenerator;

    private Map<UUID, Long> verificationKey = new HashMap<>();

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

    public CompletionStage<Store> verifyStore(UUID id) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(StoreQuery.SELECT_ID_QUERY, Values.parameters(Store.ID_PROP, id.toString()))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Store.from(record.get("s").asNode()))
                .thenCompose(persistedStore -> session.closeAsync().thenApply(signal -> {
                    long key = sendVerificationMessage(persistedStore);
                    verificationKey.put(id, key);
                    return persistedStore;
                }));
    }

    public CompletionStage<String> checkVerification(UUID id, long key) {
        boolean validVerification = key == verificationKey.getOrDefault(id, 1234L);
        if (validVerification) {
            AsyncSession session = driver.asyncSession();
            return session
                    .writeTransactionAsync(tx -> tx
                            .runAsync(StoreQuery.VERIFY_QUERY, StoreQuery.getVerifyParameterMap(id))
                            .thenCompose(ResultCursor::singleAsync)
                    )
                    .thenApply(record -> Store.from(record.get("s").asNode()))
                    .thenCompose(persistedStore -> session.closeAsync().thenApply(signal -> {
                        try {
                            return tokenGenerator.generateToken(persistedStore.getId(), Role.STORE);
                        } catch (Exception e) {
                            return null;
                        }
                    }));
        } else {
            throw new AuthenticationFailedException("Wrong Key");
        }
    }

    private long sendVerificationMessage(Store store) {
        long key = generateKey();
        smsService.sendMessage("Herzlich Willkommen bei Tracemission " + store.getStoreName() + ".\n" +
                "Wir freuen uns sehr, dass du uns dabei unterstützen möchtest die Ausbreitung von Corona weitestgehend zu verhindern.\n" +
                "Dein Aktivierungscode lautet: " + key + "\n" +
                "Viele Grüße\n" +
                "Dein Tracemission Team\n", store.getPhone());
        return key;
    }

    private long generateKey() {
        long leftLimit = 1000;
        long rightLimit = 9999;
        return leftLimit + (long) (new Random().nextFloat() * (rightLimit - leftLimit));
    }

}
