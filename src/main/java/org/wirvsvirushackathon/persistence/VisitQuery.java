package org.wirvsvirushackathon.persistence;

import org.wirvsvirushackathon.model.Visit;

import java.util.HashMap;
import java.util.Map;

public class VisitQuery {

    private VisitQuery() {
    }

    public static final String CHECKIN_QUERY = "MATCH (p:PERSON {id:$person_id}), (s:STORE {id:$store_id}) MERGE (p)-[r:VISITED {checkin:datetime(),checkout:datetime()+duration({minutes:s.avgStayInMinutes})}]->(s) RETURN r";
    public static final String CHECKOUT_QUERY = "MATCH (p:PERSON {id:$person_id})-[r]-(s:STORE {id:$store_id}) WITH r ORDER BY r.id LIMIT 1 SET r.checkout=datetime() RETURN r";

    public static Map<String, Object> getParameterMap(Visit data) {
        Map<String, Object> params = new HashMap<>();
        params.put(Visit.PERSON_ID_PROP, data.getPersonId().toString());
        params.put(Visit.STORE_ID_PROP, data.getStoreId().toString());
        return params;
    }

}
