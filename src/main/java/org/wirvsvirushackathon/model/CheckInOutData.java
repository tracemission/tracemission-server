package org.wirvsvirushackathon.model;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class CheckInOutData {

    private static final String PERSONID_PROP = "personId";
    private static final String STOREID_PROP = "storeId";
    private static final String TIMESTAMP_PROP = "timestamp";

    @NotBlank(message = "Person ID should not be blank.")
    private long personId;
    @NotBlank(message = "Store ID should not be blank.")
    private long storeId;
    @NotBlank(message = "Timestamp should not be blank.")
    private Date timestamp;

    public CheckInOutData(long personId, long storeId, Date timestamp) {
        this.personId = personId;
        this.storeId = storeId;
        this.timestamp = timestamp;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
