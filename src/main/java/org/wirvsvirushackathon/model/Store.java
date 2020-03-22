package org.wirvsvirushackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.driver.types.Node;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


public class Store {

    public static final String ID_PROP = "id";
    public static final String EMAIL_PROP = "email";
    public static final String STORE_NAME_PROP = "store_name";
    public static final String PHONE_PROP = "phone";
    public static final String CITY_PROP = "city";
    public static final String STREET_PROP = "street";
    public static final String HOUSE_NUMBER_PROP = "house_number";
    public static final String VERIFIED_PROP = "verified";
    public static final String AVG_STAY_IN_MINUTES = "avgStayInMinutes";

    @JsonIgnore
    private long sessionId;

    private UUID id = UUID.randomUUID();
    @Email
    @NotBlank(message = "Email should not be blank.")
    private String email;
    @NotBlank(message = "Store name should not be blank.")
    private String storeName;
    @NotBlank(message = "Phone number should not be blank.")
    private String phone;
    @NotBlank(message = "City should not be blank.")
    private String city;
    @NotBlank(message = "Street should not be blank.")
    private String street;
    @NotBlank(message = "House Number should not be blank.")
    private String houseNumber;

    @JsonIgnore
    private boolean verified = false;


    @JsonIgnore
    private int avgStayInMinutes = 30;

    public Store() {
    }

    public Store(long sessionId, UUID id, String email, String storeName, String phone, String city, String street, String houseNumber, boolean verified, int avgStayInMinutes) {
        this.sessionId = sessionId;
        this.id = id;
        this.email = email;
        this.storeName = storeName;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.verified = verified;
        this.avgStayInMinutes = avgStayInMinutes;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public long getSessionId() {
        return sessionId;
    }

    public boolean isVerified() {
        return verified;
    }

    public int getAvgStayInMinutes() {
        return avgStayInMinutes;
    }

    public static Store from(Node node) {
        return new Store(node.id(), UUID.fromString(node.get(ID_PROP).asString()), node.get(EMAIL_PROP).asString(), node.get(STORE_NAME_PROP).asString(), node.get(PHONE_PROP).asString(),
                node.get(CITY_PROP).asString(), node.get(STREET_PROP).asString(), node.get(HOUSE_NUMBER_PROP).asString(), node.get(VERIFIED_PROP).asBoolean(), node.get(AVG_STAY_IN_MINUTES).asInt(30));
    }

}
