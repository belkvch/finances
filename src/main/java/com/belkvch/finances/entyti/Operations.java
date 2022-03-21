package com.belkvch.finances.entyti;

import java.math.BigDecimal;
import java.util.Date;

public class Operations {
    private int id;
    private String nameOfOperation;
    private Date dateOfOperation;
    private BigDecimal priceOfOperation;

    public Operations() {
    }

    public Operations(int id, String nameOfOperation, Date dateOfOperation, BigDecimal priceOfOperation) {
        this.id = id;
        this.nameOfOperation = nameOfOperation;
        this.dateOfOperation = dateOfOperation;
        this.priceOfOperation = priceOfOperation;
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
}
