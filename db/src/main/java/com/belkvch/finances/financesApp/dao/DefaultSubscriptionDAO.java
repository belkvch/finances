package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.Operations;
import com.belkvch.finances.financesApp.entyti.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultSubscriptionDAO implements SubscriptionDAO {
    private static volatile DefaultSubscriptionDAO instance;
    private static final String SELECT_ALL = "select * from subscriptions, operations where subscriptions.oper_id = operations.id";
    private static final String SELECT_ALL_FOR_ACCOUNT = "select * from subscriptions, operations where subscriptions.account_id=? and subscriptions.oper_id = operations.id ORDER BY subscriptions.id";
    private static final String INSERT_SUBSCRIPTION = "insert into subscriptions(name,is_active,account_id,oper_id)  VALUES(?,?,?,?)";
    private static final String SELECT_SUBSCRIPTION_BY_ID = "select * from subscriptions, operations where subscriptions.id = ? and subscriptions.oper_id = operations.id";
    private static final String UPDATE_SUBSCRIPTION_STATUS = "update subscriptions set is_active = 'false' where id = ?";
    private static final String UPDATE_SUBSCRIPTION_STATUS_TO_TRUE = "update subscriptions set is_active = 'true' where id = ?";
    private static final String DELETE_SUBSCRIPTION = "delete from subscriptions where id = ?";

    private DefaultSubscriptionDAO() {
    }

    public static DefaultSubscriptionDAO getInstance() {
        if (instance == null) {
            synchronized (DefaultSubscriptionDAO.class) {
                if (instance == null) {
                    instance = new DefaultSubscriptionDAO();
                }
            }
        }
        return instance;
    }

    public Subscription initSubscription(ResultSet resultSet) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setId(resultSet.getInt("id"));
        subscription.setName(resultSet.getString("name"));
        subscription.setActive(resultSet.getBoolean("is_active"));
        subscription.setAccountId(resultSet.getInt("account_id"));
        subscription.setOperation(new Operations(resultSet.getInt("oper_id"), resultSet.getBigDecimal("salary")));
        return subscription;
    }

    @Override
    public List<Subscription> showAllSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                subscriptions.add(initSubscription(resultSet));
            }
        } catch(
                SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return subscriptions;
    }

    @Override
    public List<Subscription> showAllSubscriptionsForAccount(int id) {
        List<Subscription> subscriptions = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FOR_ACCOUNT);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                subscriptions.add(initSubscription(resultSet));
            }
        } catch(
                SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return subscriptions;
    }

    @Override
    public Subscription addNewSubscription(Subscription subscription) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUBSCRIPTION);
            preparedStatement.setString(1, subscription.getName());
            preparedStatement.setBoolean(2, subscription.isActive());
            preparedStatement.setInt(3, subscription.getAccountId());
            preparedStatement.setInt(4, subscription.getOperation().getId());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    subscription.setId(resultSet.getInt("id"));
                }
            }
            return subscription;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Subscription getSubscriptionById(int id) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SUBSCRIPTION_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initSubscription(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Subscription changeSubscriptionToFalse(Subscription subscription) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SUBSCRIPTION_STATUS);
            preparedStatement.setInt(1, subscription.getId());
            preparedStatement.executeUpdate();
            return subscription;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Subscription deleteSubscription(Subscription subscription) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SUBSCRIPTION);
            preparedStatement.setInt(1, subscription.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Subscription changeSubscriptionToTrue(Subscription subscription) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SUBSCRIPTION_STATUS_TO_TRUE);
            preparedStatement.setInt(1, subscription.getId());
            preparedStatement.executeUpdate();
            return subscription;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
