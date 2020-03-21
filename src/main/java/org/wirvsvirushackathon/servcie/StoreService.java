package org.wirvsvirushackathon.servcie;

import org.wirvsvirushackathon.model.Store;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class StoreService {

    private Map<Long, Store> stores = new HashMap<>();

    public void registerStore(Store store) {
        store.setId(getRandomId());
        stores.put(store.getId(), store);
    }

    public Store getStoreById(long id){
        return stores.get(id);
    }

    private long getRandomId() {
        return 100000000000000L + (long) (Math.random() * (999999999999999L - 10000000000L));
    }

}
