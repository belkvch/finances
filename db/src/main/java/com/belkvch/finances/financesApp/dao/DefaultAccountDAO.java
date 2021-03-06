package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.Accounts;
import com.belkvch.finances.financesApp.entyti.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultAccountDAO implements AccountDAO {
    private static volatile DefaultAccountDAO instance;

    private static final String SELECT_ALL_FOR_USER = "select * from accounts,users,currency, account_user " +
            "where users.id = ?" +
            "and accounts.currency_id=currency.id " +
            "and account_user.user_id=users.id and account_user.account_id = accounts.id " +
            "and accounts.is_active_account = 'true' " +
            "ORDER BY accounts.id";

    private static final String SELECT_ALL_FOR_USER_ARCHIVE = "select * from accounts,users,currency, account_user " +
            "where users.id = ?" +
            "and accounts.currency_id=currency.id " +
            "and account_user.user_id=users.id and account_user.account_id = accounts.id " +
            "and accounts.is_active_account = 'false' " +
            "ORDER BY accounts.id";

    private static final String INSERT_ACCOUNT = "insert into accounts(currency_id,amount)  VALUES(?,?)";

    private static final String SELECT_ACCOUNT_BY_ID = "select * from accounts,users,currency,account_user where accounts.id = ?"+
            "and accounts.currency_id=currency.id " +
            "and account_user.user_id=users.id and account_user.account_id = accounts.id " +
            "ORDER BY accounts.id";

    private static final String UPDATE_ACCOUNT_AMOUNT = "update accounts set amount = ? where id = ?";

    private static final String SELECT_MAX_ACCOUNT = "SELECT * FROM accounts, currency" +
            " WHERE accounts.id = (SELECT MAX (accounts.id) FROM accounts);";

    private static final String SELECT_ALL = "select * from accounts,users,currency, account_user " +
            "where accounts.currency_id=currency.id " +
            "and account_user.user_id=users.id and account_user.account_id = accounts.id " +
            "ORDER BY accounts.id";

    private static final String INSERT_USER_ACCOUNT = "insert into account_user(user_id, account_id)  VALUES(?,?)";

    private static final String UPDATE_ACCOUNT_STATUS = "update accounts set is_active_account = 'false' where id = ?";

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
        accounts.setCurrencyId(new Currency(resultSet.getInt("currency_id"), resultSet.getString("name")));
        accounts.setActiveAccount(resultSet.getBoolean("is_active_account"));
        return accounts;
    }

    @Override
    public List<Accounts> showAllAccounts() {
        List<Accounts> accounts = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
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
    public List<Accounts> showAllAccountsForUser(int userId) {
        List<Accounts> accounts = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FOR_USER);
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
    public List<Accounts> showAllAccountsForUserArchive(int userId) {
        List<Accounts> accounts = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FOR_USER_ARCHIVE);
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
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, accounts.getCurrencyId().getId());
            preparedStatement.setBigDecimal(2, accounts.getAmount());
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
    public Accounts addUserAccountConn(Accounts account, int userId) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ACCOUNT);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, account.getId());
            preparedStatement.executeUpdate();
            return account;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Accounts changeAccountStatus(Accounts account) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_STATUS);
            preparedStatement.setInt(1, account.getId());
            preparedStatement.executeUpdate();
            return account;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}