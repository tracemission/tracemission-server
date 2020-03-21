package org.wirvsvirushackathon.model;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

public class CheckInOutData {

    private static final String PERSONID_PROP = "personId";
    private static final String STOREID_PROP = "storeId";
    private static final String TIMESTAMP_PROP = "timestamp";

    @NotBlank(message = "Person ID should not be blank.")
    private UUID personId;
    @NotBlank(message = "Store ID should not be blank.")
    private UUID storeId;
    @NotBlank(message = "Timestamp should not be blank.")
    private Date timestamp;

    public CheckInOutData(UUID personId, UUID storeId, Date timestamp) {
        this.personId = personId;
        this.storeId = storeId;
        this.timestamp = timestamp;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
