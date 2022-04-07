package com.belkvch.finances.financesApp.entyti;

import java.math.BigDecimal;
import java.util.Currency;

public class Accounts {
    private int id;
    private BigDecimal amount;
    private Currency currencyId;
    private User userId;

    public Accounts() {
    }

    public Accounts(BigDecimal amount, Currency currencyId, User userId) {
        this.amount = amount;
        this.currencyId = currencyId;
        this.userId = userId;
    }

    public Accounts(int id, BigDecimal amount, Currency currencyId, User userId) {
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

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
