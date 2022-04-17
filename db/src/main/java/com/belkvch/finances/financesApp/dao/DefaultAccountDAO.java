package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.Accounts;
import com.belkvch.finances.financesApp.entyti.Currency;
import com.belkvch.finances.financesApp.entyti.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultAccountDAO implements AccountDAO {
    private static volatile DefaultAccountDAO instance;
    private static final String SELECT_ALL = "select * from accounts,users,currency where accounts.user_id = ? " +
            "and accounts.currency_id=currency.id and accounts.user_id=users.id";
    private static final String INSERT_ACCOUNT = "insert into accounts(currency_id,user_id,amount)  VALUES(?,?,?)";
    private static final String SELECT_ACCOUNT_BY_ID = "select * from accounts,users,currency where accounts.id = ?";
    private static final String UPDATE_ACCOUNT_AMOUNT = "update accounts set amount = ? where id = ?";
    private static final String SELECT_MAX_ACCOUNT = "SELECT * FROM accounts, currency" +
            " WHERE accounts.id = (SELECT MAX (accounts.id) FROM accounts);";

    private static final String INSERT_CATEGORY_ACCOUNT = "insert into account_category(account_id, category_id)  VALUES(?,1), (?,2), (?,3), (?,4), (?,5)";

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

    public Accounts initAccount(ResultSet resultSet) throws SQLException {
        Accounts accounts = new Accounts();
        accounts.setId(resultSet.getInt("id"));
        accounts.setAmount(resultSet.getBigDecimal("amount"));
        accounts.setCurrencyId(new Currency(resultSet.getInt("id"), resultSet.getString("name")));
        accounts.setUserId(new User(resultSet.getInt("user_id")));
        return accounts;
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

    @Override
    public Accounts addNewAccount(Accounts accounts) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT);
            preparedStatement.setObject(1, accounts.getCurrencyId().getId());
            preparedStatement.setObject(2, accounts.getUserId().getId());
            preparedStatement.setBigDecimal(3, accounts.getAmount());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    accounts.setId(resultSet.getInt("id"));
                }
            }
            return accounts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Accounts getAccountById(int id) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initAccount(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Accounts changeOperationAmount(Accounts account) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_AMOUNT);
            preparedStatement.setBigDecimal(1, account.getAmount());
            preparedStatement.setInt(2, account.getId());
            preparedStatement.executeUpdate();
            return account;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Accounts getLastAccount() {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MAX_ACCOUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initAccount(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Accounts getAccountCategoryConn(Accounts newAccount) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY_ACCOUNT);
            preparedStatement.setInt(1, newAccount.getId());
            preparedStatement.setInt(2, newAccount.getId());
            preparedStatement.setInt(3, newAccount.getId());
            preparedStatement.setInt(4, newAccount.getId());
            preparedStatement.setInt(5, newAccount.getId());
            preparedStatement.executeUpdate();
            return newAccount;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return null;
    }

}