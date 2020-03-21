package org.wirvsvirushackathon.model;

import org.neo4j.driver.types.Node;

import javax.validation.constraints.NotBlank;


public class Store {

    private static final String EMAIL_PROP = "email";
    private static final String STORE_NAME_PROP = "store_name";
    private static final String PHONE_PROP = "phone";
    private static final String CITY_PROP = "city";
    private static final String STREET_PROP = "street";
    private static final String HOUSENUMBER_PROP = "housenumber";

    private long id;

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
    private String housenumber;



    public Store(long id, String email, String storeName, String phone, String city, String street, String housenumber) {
        this.id = id;
        this.email = email;
        this.storeName = storeName;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.housenumber = housenumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getHousenumber() {
        return housenumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public static Store from(Node node) {
        return new Store(node.id(), node.get(EMAIL_PROP).asString(), node.get(STORE_NAME_PROP).asString(),node.get(PHONE_PROP).asString(),
                                    node.get(CITY_PROP).asString(), node.get(STREET_PROP).asString(), node.get(HOUSENUMBER_PROP).asString());
    }

}
