package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultUserDAO implements UserDAO {
    private static volatile DefaultUserDAO instance;
    private static final String INSERT_USER = "insert into users(username, password, role) values (?, ?, ?)";
    private static final String SELECT_USER_BY_LOGIN = "select * from users where username = ?";
    private static final String SELECT_USER_BY_LOGIN_PASSWORD = "select * from users where username = ? and password = ?";

    public static DefaultUserDAO getInstance() {
        if (instance == null) {
            synchronized (DefaultUserDAO.class) {
                if (instance == null) {
                    instance = new DefaultUserDAO();
                }
            }
        }
        return instance;
    }

    public User initUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setPassword(resultSet.getString("role"));
        return user;
    }

    @Override
    public User getByLogin(String login) {
        try(Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return initUser(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User addUser(User user) {
        try(Connection connection = DBManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            user.setRole("USER");
            preparedStatement.setString(3, user.getRole());
            preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()){
                    user.setId(resultSet.getInt("id"));
                }
            }
            return user;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public User getByLoginPassword(String login, String password) {
        try(Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return initUser(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
