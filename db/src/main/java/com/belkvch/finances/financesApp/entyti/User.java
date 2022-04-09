package com.belkvch.finances.financesApp.entyti;

import java.util.List;

public class User {
    private int id;
    private String login;
    private String password;
    private Role roleId;

    public User() {
    }

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, Role roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
    }

    public User(String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role role) {
        this.roleId = role;
    }

}
