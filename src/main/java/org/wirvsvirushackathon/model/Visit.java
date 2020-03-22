package org.wirvsvirushackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.driver.types.Relationship;

import java.util.UUID;

public class Visit {

    public static final String PERSON_ID_PROP = "person_id";
    public static final String STORE_ID_PROP = "store_id";
    public static final String CHECKIN = "checkin";
    public static final String CHECKOUT = "checkout";

    @JsonIgnore
    private long id;

    private UUID personId;
    private UUID storeId;

    private String checkin = null;
    private String checkout = null;

    public Visit() {
    }

    public Visit(long id, UUID personId, UUID storeId, String checkin, String checkout) {
        this.id = id;
        this.personId = personId;
        this.storeId = storeId;
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public long getId() {
        return id;
    }

    public UUID getPersonId() {
        return personId;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public static Visit from(Relationship relationship) {
        return new Visit(relationship.id(), UUID.fromString(relationship.get(PERSON_ID_PROP).asString()), UUID.fromString(relationship.get(STORE_ID_PROP).asString()), relationship.get(CHECKIN).asString(),
                relationship.get(CHECKOUT).asString());
    }
}
