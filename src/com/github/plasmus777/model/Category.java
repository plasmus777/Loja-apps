package com.github.plasmus777.model;

public class Category {
    private long id;
    private String name;
    private String description;

    public Category(String name, String description){
        setName(name);
        setDescription(description);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
