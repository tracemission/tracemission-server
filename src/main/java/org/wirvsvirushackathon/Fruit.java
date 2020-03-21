package org.wirvsvirushackathon;

import org.neo4j.driver.types.Node;

public class Fruit {

    private Long id;

    private String name;

    public Fruit(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Fruit from(Node node) {
        return new Fruit(node.id(), node.get("name").asString());
    }
}
