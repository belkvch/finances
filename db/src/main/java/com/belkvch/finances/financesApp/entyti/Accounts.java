package com.belkvch.finances.financesApp.entyti;

import java.math.BigDecimal;

public class Accounts {
    private int id;
    private BigDecimal amount;
    private int currencyId;
    private int userId;

    public Accounts() {
    }

    public Accounts(BigDecimal amount, int currencyId, int userId) {
        this.amount = amount;
        this.currencyId = currencyId;
        this.userId = userId;
    }

    public Accounts(int id, BigDecimal amount, int currencyId, int userId) {
        this.id = id;
        this.amount = amount;
        this.currencyId = currencyId;
        this.userId = userId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
