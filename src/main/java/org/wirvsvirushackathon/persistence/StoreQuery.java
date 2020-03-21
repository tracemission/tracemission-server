package org.wirvsvirushackathon.persistence;

import org.wirvsvirushackathon.model.Store;

import java.util.HashMap;
import java.util.Map;

public class StoreQuery {

    private StoreQuery() {
    }

    public static final String CREATE_QUERY = "CREATE(s:STORE{id:$id,email:$email,store_name:$store_name,phone:$phone,city:$city,street:$street,house_number:$house_number}) RETURN s";
    public static final String SELECT_ID_QUERY = "MATCH(s:STORE{id:$id}) RETURN s";

    public static Map<String, Object> getParameterMap(Store store) {
        Map<String, Object> params = new HashMap<>();
        params.put(Store.ID_PROP, store.getId().toString());
        params.put(Store.EMAIL_PROP, store.getEmail());
        params.put(Store.STORE_NAME_PROP, store.getStoreName());
        params.put(Store.PHONE_PROP, store.getPhone());
        params.put(Store.CITY_PROP, store.getCity());
        params.put(Store.STREET_PROP, store.getStreet());
        params.put(Store.HOUSE_NUMBER_PROP, store.getHouseNumber());

        return params;
    }

}
