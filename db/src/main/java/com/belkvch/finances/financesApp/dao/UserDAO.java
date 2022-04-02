package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.entyti.User;

public interface UserDAO {
    User getByLogin (String login);
    User addUser(User user);
    User getByLoginPassword(String login, String password);
}
