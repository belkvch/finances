package com.belkvch.finances.financesApp.entyti;

public class Category {
    private int id;
    private String name;
    private boolean isNecessary;

    public Category(boolean isNecessary) {
        this.isNecessary = isNecessary;
    }

    public Category(int id, String name, boolean isNecessary) {
        this.id = id;
        this.name = name;
        this.isNecessary = isNecessary;
    }

    public Category(String name, boolean isNecessary) {
        this.name = name;
        this.isNecessary = isNecessary;
    }

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id) {
        this.id = id;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNecessary() {
        return isNecessary;
    }

    public void setNecessary(boolean necessary) {
        isNecessary = necessary;
    }
}
