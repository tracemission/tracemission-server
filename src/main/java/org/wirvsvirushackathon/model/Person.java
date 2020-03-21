package org.wirvsvirushackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.driver.types.Node;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


public class Person {

    public static final String ID_PROP = "id";
    public static final String FIRST_NAME_PROP = "first_name";
    public static final String LAST_NAME_PROP = "last_name";
    public static final String PHONE_PROP = "phone";

    @JsonIgnore
    private long sessionId;

    private UUID id = UUID.randomUUID();
    @NotBlank(message = "First name should not be blank.")
    private String firstName;
    @NotBlank(message = "Last name should not be blank.")
    private String lastName;
    @NotBlank(message = "Phone number should not be blank.")
    private String phone;

    public Person() {
    }

    public Person(long sessionId, UUID id, String firstName, String lastName, String phone) {
        this.sessionId = sessionId;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        return new Person(node.id(), UUID.fromString(node.get(ID_PROP).asString()), node.get(FIRST_NAME_PROP).asString(), node.get(LAST_NAME_PROP).asString(), node.get(PHONE_PROP).asString());
    }

}
