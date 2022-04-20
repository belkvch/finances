package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/edit-user")
public class EditUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        int userId = (int) httpSession.getAttribute("id");
        User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
        Role roleBan = new Role("BAN");
        if (currenUser.getRoleId().getName().equals(roleBan.getName())) {
            request.getRequestDispatcher("/ban.jsp").forward(request, response);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = DefaultUserDAO.getInstance().getUserById(id);
            if (user != null && user.getId() == userId) {
                List<User> users = new ArrayList<>();
                users.add(user);
                request.setAttribute("users", users);
                getServletContext().getRequestDispatcher("/edit-user.jsp").forward(request, response);
            } else {
                response.sendRedirect("/accounts");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("edit".equals(request.getParameter("actionType"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = DefaultUserDAO.getInstance().getUserById(id);
            if (user != null) {
                String oldPassword = request.getParameter("oldPassword");
                String newLogin = request.getParameter("loginNew");
                String newPassword = request.getParameter("password");
                if (BCrypt.checkpw(oldPassword, user.getPassword())) {
                    if (newLogin == null || newLogin.isEmpty() || newLogin.trim().isEmpty()) {
                        response.sendRedirect("/error");
                    } else {
                        user.setLogin(newLogin);
                        DefaultUserDAO.getInstance().changeUserLogin(user);
                    }

                    if (newPassword == null || newPassword.isEmpty() || newPassword.trim().isEmpty()) {
                        response.sendRedirect("/error");
                    } else {
                        user.setPassword(newPassword);
                        DefaultUserDAO.getInstance().changeUserPassword(user);
                    }
                }
                    response.sendRedirect("/edit-user?id=" + user.getId());
            }
        } else {
            response.sendRedirect("/error");
        }
    }
}
