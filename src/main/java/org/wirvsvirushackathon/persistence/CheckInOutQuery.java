package org.wirvsvirushackathon.persistence;

import org.wirvsvirushackathon.model.CheckInOutData;
import org.wirvsvirushackathon.model.Person;

import java.util.HashMap;
import java.util.Map;

public class CheckInOutQuery {

    private CheckInOutQuery() {
    }

    public static final String CHECKIN_QUERY = "MATCH (a:PERSON{id:$personId}), (b:STORE {id:$storeId}) MERGE (a)-[r:VISITED {checkin:datetime()}]->(b)";
    public static final String CHECKOUT_QUERY = "MATCH (a:PERSON{id:$personId})-[r]-(b:STORE {id:$storeId}) WHERE NOT EXISTS(r.checkout) SET r.checkout = datetime()";

    public static Map<String, Object> getParameterMap(CheckInOutData data) {
        Map<String, Object> params = new HashMap<>();
        params.put(CheckInOutData.PERSONID_PROP, data.getPersonId().toString());
        params.put(CheckInOutData.STOREID_PROP, data.getStoreId().toString());
        return params;
    }

}
