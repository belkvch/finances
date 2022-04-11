package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.Currency;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DefaultCurrencyDAO implements CurrencyDAO{
    private static volatile DefaultCurrencyDAO instance;
    private static final String SELECT_ALL = "select * from currency";

    private DefaultCurrencyDAO() {
    }

    public static DefaultCurrencyDAO getInstance() {
        if (instance == null) {
            synchronized (DefaultCurrencyDAO.class) {
                if (instance == null) {
                    instance = new DefaultCurrencyDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Currency> showAllCurrency() {
        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                currencies.add(initCurrency(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return currencies;
    }

    public Currency initCurrency(ResultSet resultSet) throws SQLException {
        Currency currency = new Currency();
        currency.setId(resultSet.getInt("id"));
        currency.setName(resultSet.getString("name"));
        return currency;
    }
}
