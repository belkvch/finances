package com.belkvch.finances.financesApp.entyti;

import java.math.BigDecimal;

public class Accounts {
    private int id;
    private BigDecimal amount;
    private Currency currencyId;
    private boolean isActiveAccount;

    public Accounts() {
    }

    public Accounts(int id) {
        this.id = id;
    }

    public Accounts(BigDecimal amount, Currency currencyId) {
        this.amount = amount;
        this.currencyId = currencyId;
    }

    public Accounts(int id, BigDecimal amount, Currency currencyId) {
        this.id = id;
        this.amount = amount;
        this.currencyId = currencyId;
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

    public boolean isActiveAccount() {
        return isActiveAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        isActiveAccount = activeAccount;
    }

}
