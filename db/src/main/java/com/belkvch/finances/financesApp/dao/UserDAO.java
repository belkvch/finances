package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.entyti.User;

import java.util.List;

public interface UserDAO {
    User getByLogin (String login);

    User addUser(User user);

    User getByLoginPassword(String login, String password);

    User getPassword(String login);

    List<User> showAllUsers();

    User getUserById(int id);

    User changeUserRole(User user);

    User changeUserLogin(User user);

    User changeUserPassword(User user);

    User getUserByAccountId(int id, int UserId);
}
