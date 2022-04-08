package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.Accounts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultAccountDAO implements AccountDAO {
    private static volatile DefaultAccountDAO instance;
    private static final String SELECT_ALL = "select * from accounts where user_id = ?";

    private DefaultAccountDAO() {
    }

    public static DefaultAccountDAO getInstance() {
        if (instance == null) {
            synchronized (DefaultAccountDAO.class) {
                if (instance == null) {
                    instance = new DefaultAccountDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Accounts> showAllAccountsForUser(int userId) {
        List<Accounts> accounts = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(initAccount(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accounts;
    }

    public Accounts initAccount(ResultSet resultSet) throws SQLException {
        Accounts accounts = new Accounts();
        accounts.setId(resultSet.getInt("id"));
        accounts.setAmount(resultSet.getBigDecimal("amount"));
        accounts.setCurrencyId(resultSet.getInt("currency_id"));
        accounts.setUserId(resultSet.getInt("user_id"));
        return accounts;
    }
}
