package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.Role;
import com.belkvch.finances.financesApp.entyti.User;
import org.slf4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/update-user")
public class UpdateUserServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession(true);
            int userId = (int) httpSession.getAttribute("id");
            User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
            Role roleAdmin = new Role("ADMIN");
            if (currenUser.getRoleId().getName().equals(roleAdmin.getName())) {
                int id = Integer.parseInt(request.getParameter("id"));
                User user = DefaultUserDAO.getInstance().getUserById(id);
                if (user != null) {
                    List<User> users = new ArrayList<>();
                    users.add(user);
                    request.setAttribute("users", users);
                    getServletContext().getRequestDispatcher("/update-user.jsp").forward(request, response);
                } else {
                    LOGGER.info("user is empty");
                    response.sendRedirect("/error");
                }
            } else {
                response.sendRedirect("/accounts");
            }
        } catch (NumberFormatException e) {
            LOGGER.info("NumberFormatException in doGet in UpdateUserServlet");
            response.sendRedirect("/error");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("update".equals(request.getParameter("actionType"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = DefaultUserDAO.getInstance().getUserById(id);
            if (user != null) {
                int role = Integer.parseInt(request.getParameter("role_id"));
                if (role == 0) {
                    LOGGER.info("role is 0");
                    response.sendRedirect("/error");
                } else {
                    if (user.getRoleId().getId() != 2) {
                        user.setRoleId(new Role(role));
                        DefaultUserDAO.getInstance().changeUserRole(user);
                    } else {
                        LOGGER.info("user is admin");
                        response.sendRedirect("/error");
                    }
                    response.sendRedirect("/admin");
                }
            }
        }
    }
}