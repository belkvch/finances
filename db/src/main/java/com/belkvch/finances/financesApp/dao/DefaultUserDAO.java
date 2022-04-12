package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.Role;
import com.belkvch.finances.financesApp.entyti.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class DefaultUserDAO implements UserDAO {
    private static volatile DefaultUserDAO instance;
    private static final String SELECT_ALL = "select * from users,roles where users.role_id = roles.id";
    private static final String INSERT_USER = "insert into users(username, password, role_id) values (?, ?, ?)";
    private static final String SELECT_USER_BY_LOGIN = "select * from users,roles where username = ?";
    private static final String SELECT_USER_BY_LOGIN_PASSWORD = "select * from users,roles where username = ? and password = ?";
    private static final String SELECT_PASSWORD = "select password from users where username = ?";
    private static final String SELECT_USER_BY_ID = "select * from users,roles where users.id = ? AND users.role_id = roles.id";
    private static final String UPDATE_USER_ROLE = "update users set role_id = ? from roles where users.id = ? AND users.role_id = roles.id";

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
        user.setRoleId(new Role(resultSet.getInt("id"), resultSet.getString("name")));
        return user;
    }

    @Override
    public User getByLogin(String login) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initUser(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User addUser(User user) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
            String passwordHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
            preparedStatement.setString(1, user.getLogin());
//            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(2, passwordHash);
            user.setRoleId(new Role(1,"USER"));
            preparedStatement.setObject(3, user.getRoleId().getId());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
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
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initUser(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User getPassword(String login) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PASSWORD);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User hashUser = initUser(resultSet);
                return hashUser;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> showAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                users.add(initUser(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initUser(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User changeUserRole(User user) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_ROLE);
            preparedStatement.setInt(1, user.getRoleId().getId());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
            return user;
//            ResultSet resultSet = preparedStatement.executeUpdate();
//            if (resultSet.next()) {
//                return initUser(resultSet);
//            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}


