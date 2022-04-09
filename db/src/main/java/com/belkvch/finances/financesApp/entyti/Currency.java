package com.belkvch.finances.financesApp.entyti;

public class Currency {
    private int id;
    private String name;

    public Currency(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Currency(int id) {
        this.id = id;
    }

    public Currency(String name) {
        this.name = name;
    }

    public Currency() {
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
}
