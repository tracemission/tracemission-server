package org.wirvsvirushackathon.model;

import org.neo4j.driver.types.Node;

import javax.validation.constraints.NotBlank;


public class Person {

    private static final String EMAIL_PROP = "email";
    private static final String FIRST_NAME_PROP = "first_name";
    private static final String LAST_NAME_PROP = "last_name";
    private static final String PHONE_PROP = "phone";

    private long id;

    @NotBlank(message = "Email should not be blank.")
    private String email;
    @NotBlank(message = "First name should not be blank.")
    private String firstName;
    @NotBlank(message = "Last name should not be blank.")
    private String lastName;
    @NotBlank(message = "Phone number should not be blank.")
    private String phone;


    public Person(long id, String email, String firstName, String lastName, String phone) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static Person from(Node node) {
        return new Person(node.id(), node.get(EMAIL_PROP).asString(), node.get(FIRST_NAME_PROP).asString(), node.get(LAST_NAME_PROP).asString(),node.get(PHONE_PROP).asString());
    }

}
