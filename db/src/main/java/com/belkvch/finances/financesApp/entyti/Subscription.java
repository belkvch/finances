package com.belkvch.finances.financesApp.entyti;

public class Subscription {
    private int id;
    private String name;
    private boolean isActive;
    private int accountId;
    private Operations operation;

    public Subscription() {
    }

    public Subscription(int id, String name, boolean isActive, int accountId) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.accountId = accountId;
    }

    public Subscription(String name, boolean isActive, int accountId) {
        this.name = name;
        this.isActive = isActive;
        this.accountId = accountId;
    }

    public Subscription(String name, boolean isActive, int accountId, Operations operation) {
        this.name = name;
        this.isActive = isActive;
        this.accountId = accountId;
        this.operation = operation;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Operations getOperation() {
        return operation;
    }

    public void setOperation(Operations operation) {
        this.operation = operation;
    }
}
