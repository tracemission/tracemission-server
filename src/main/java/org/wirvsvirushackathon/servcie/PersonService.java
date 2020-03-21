package org.wirvsvirushackathon.servcie;

import org.wirvsvirushackathon.model.Person;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class PersonService {

    private Map<Long, Person> persons = new HashMap<>();

    public void registerPerson(Person person) {
        person.setId(getRandomId());
        persons.put(person.getId(), person);
    }

    public Person getPersonById(long id){
        return persons.get(id);
    }

    private long getRandomId() {
        return 100000000000000L + (long) (Math.random() * (999999999999999L - 10000000000L));
    }

}
