package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultCurrencyDAO implements CurrencyDAO{
    private static volatile DefaultCurrencyDAO instance;
    private static final String SELECT_ALL = "select * from currency";
    private static final String SELECT_CURRENCY_BY_ID = "SELECT * FROM currency WHERE id = ?";

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

    @Override
    public Currency findById(int id) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENCY_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initCurrency(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Currency initCurrency(ResultSet resultSet) throws SQLException {
        Currency currency = new Currency();
        currency.setId(resultSet.getInt("id"));
        currency.setName(resultSet.getString("name"));
        currency.setToByn(resultSet.getBigDecimal("to_byn"));
        currency.setToEur(resultSet.getBigDecimal("to_eur"));
        currency.setToUsd(resultSet.getBigDecimal("to_usd"));
        return currency;
    }
}
