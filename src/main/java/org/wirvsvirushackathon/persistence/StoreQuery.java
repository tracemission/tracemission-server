package org.wirvsvirushackathon.persistence;

import org.wirvsvirushackathon.model.Store;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StoreQuery {

    private StoreQuery() {
    }

    public static final String CREATE_QUERY = "CREATE(s:STORE{id:$id,email:$email,store_name:$store_name,phone:$phone,city:$city,street:$street,house_number:$house_number,verified:$verified}) RETURN s";
    public static final String SELECT_ID_QUERY = "MATCH(s:STORE{id:$id}) RETURN s";
    public static final String VERIFY_QUERY = "MATCH(s:STORE{id:$id}) SET s.verified=$verified RETURN s";

    public static Map<String, Object> getParameterMap(Store store) {
        Map<String, Object> params = new HashMap<>();
        params.put(Store.ID_PROP, store.getId().toString());
        params.put(Store.EMAIL_PROP, store.getEmail());
        params.put(Store.STORE_NAME_PROP, store.getStoreName());
        params.put(Store.PHONE_PROP, store.getPhone());
        params.put(Store.CITY_PROP, store.getCity());
        params.put(Store.STREET_PROP, store.getStreet());
        params.put(Store.HOUSE_NUMBER_PROP, store.getHouseNumber());
        params.put(Store.VERIFIED_PROP, store.isVerified());

        return params;
    }

    public static Map<String, Object> getVerifyParameterMap(UUID id) {
        Map<String, Object> params = new HashMap<>();
        params.put(Store.ID_PROP, id.toString());
        params.put(Store.VERIFIED_PROP, true);

        return params;
    }

}
