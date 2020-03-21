package org.wirvsvirushackathon.model;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

public class CheckInOutData {

    private static final String PERSONID_PROP = "personId";
    private static final String STOREID_PROP = "storeId";

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
