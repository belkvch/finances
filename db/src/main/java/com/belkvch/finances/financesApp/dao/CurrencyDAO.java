package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.entyti.Currency;

import java.util.List;

public interface CurrencyDAO {
    List<Currency> showAllCurrency();

    Currency findById(int id);
}
