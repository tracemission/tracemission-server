package org.wirvsvirushackathon.persistence;

import org.wirvsvirushackathon.model.Person;

import java.util.HashMap;
import java.util.Map;

public class PersonQuery {

    private PersonQuery() {
    }

    public static final String CREATE_QUERY = "CREATE(p:PERSON{id:$id,first_name:$first_name,last_name:$last_name,phone:$phone,verified:$verified,infected:$infected}) RETURN p";
    public static final String SELECT_ID_QUERY = "MATCH(p:PERSON{id:$id}) RETURN p";
    public static final String VERIFY_QUERY = "MATCH(p:PERSON{id:$id}) SET p.verified=$verified";
    public static final String REPORT_INFECTED_QUERY = "MATCH(p:PERSON{id:$id}) SET p.infected=$infected";

    public static Map<String, Object> getParameterMap(Person person) {
        Map<String, Object> params = new HashMap<>();
        params.put(Person.ID_PROP, person.getId().toString());
        params.put(Person.FIRST_NAME_PROP, person.getFirstName());
        params.put(Person.LAST_NAME_PROP, person.getLastName());
        params.put(Person.PHONE_PROP, person.getPhone());
        params.put(Person.VERIFIED_PROP, person.isVerified());
        params.put(Person.INFECTED_PROP, person.isVerified());

        return params;
    }

}
