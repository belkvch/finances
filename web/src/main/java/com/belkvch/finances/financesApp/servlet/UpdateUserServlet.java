package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/update-user")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = DefaultUserDAO.getInstance().getUserById(id);
            if (user != null) {
                List<User> users = new ArrayList<>();
                users.add(user);
                request.setAttribute("users", users);
                getServletContext().getRequestDispatcher("/update-user.jsp").forward(request, response);
            } else {
                response.sendRedirect("/error");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/error");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("update".equals(request.getParameter("actionType"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = DefaultUserDAO.getInstance().getUserById(id);
            if (user != null) {
                int role = Integer.parseInt(request.getParameter("role"));
                if (role == 0) {
                    response.sendRedirect("/error");
                } else {
                    user.setRoleId(role);
                    DefaultUserDAO.getInstance().changeUserRole(user);
                }
            } else {
                response.sendRedirect("/error");
            }
            response.sendRedirect("/operations");
        }
    }
}