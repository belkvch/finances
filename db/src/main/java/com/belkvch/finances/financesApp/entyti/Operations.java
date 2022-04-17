package com.belkvch.finances.financesApp.entyti;

import java.math.BigDecimal;
import java.util.Date;

public class Operations {
    private int id;
    private String nameOfOperation;
    private Date dateOfOperation;
    private BigDecimal priceOfOperation;
    private int accountId;
    private Category categoryId;

    public Operations() {
    }

    public Operations(int accountId) {
        this.accountId = accountId;
    }

    public Operations(int id, String nameOfOperation, Date dateOfOperation, BigDecimal priceOfOperation, int accountId, Category categoryId) {
        this.id = id;
        this.nameOfOperation = nameOfOperation;
        this.dateOfOperation = dateOfOperation;
        this.priceOfOperation = priceOfOperation;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }

    public Operations(String nameOfOperation, Date dateOfOperation, BigDecimal priceOfOperation, int accountId, Category categoryId) {
        this.nameOfOperation = nameOfOperation;
        this.dateOfOperation = dateOfOperation;
        this.priceOfOperation = priceOfOperation;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }

    public int getId() {
        return this.id;
    }

    public String getNameOfOperation() {
        return this.nameOfOperation;
    }

    public Date getDateOfOperation() {
        return this.dateOfOperation;
    }

    public BigDecimal getPriceOfOperation() {
        return this.priceOfOperation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameOfOperation(String nameOfOperation) {
        this.nameOfOperation = nameOfOperation;
    }

    public void setDateOfOperation(Date dateOfOperation) {
        this.dateOfOperation = dateOfOperation;
    }

    public void setPriceOfOperation(BigDecimal priceOfOperation) {
        this.priceOfOperation = priceOfOperation;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
}
