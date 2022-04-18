package com.belkvch.finances.financesApp.entyti;

import java.math.BigDecimal;

public class Currency {
    private int id;
    private String name;
    private BigDecimal toByn;
    private BigDecimal toEur;
    private BigDecimal toUsd;

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

    public Currency(String name, BigDecimal toByn, BigDecimal toEur, BigDecimal toUsd) {
        this.name = name;
        this.toByn = toByn;
        this.toEur = toEur;
        this.toUsd = toUsd;
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

    public BigDecimal getToByn() {
        return toByn;
    }

    public void setToByn(BigDecimal toByn) {
        this.toByn = toByn;
    }

    public BigDecimal getToEur() {
        return toEur;
    }

    public void setToEur(BigDecimal toEur) {
        this.toEur = toEur;
    }

    public BigDecimal getToUsd() {
        return toUsd;
    }

    public void setToUsd(BigDecimal toUsd) {
        this.toUsd = toUsd;
    }
}
