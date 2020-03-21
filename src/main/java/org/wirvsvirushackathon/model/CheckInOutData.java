package org.wirvsvirushackathon.model;

import java.util.UUID;

public class CheckInOutData {

    public static final String PERSONID_PROP = "personId";
    public static final String STOREID_PROP = "storeId";

    private UUID personId;
    private UUID storeId;

    public CheckInOutData(UUID personId, UUID storeId) {
        this.personId = personId;
        this.storeId = storeId;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }
}
