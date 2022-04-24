package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultSubscriptionDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.Role;
import com.belkvch.finances.financesApp.entyti.Subscription;
import com.belkvch.finances.financesApp.entyti.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/stop-timer")
public class StopSubscriptionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        int userId = (int) httpSession.getAttribute("id");
        User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
        Role roleBan = new Role("BAN");
        if (currenUser.getRoleId().getName().equals(roleBan.getName())) {
            request.getRequestDispatcher("/ban.jsp").forward(request, response);
        } else {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Subscription subscription = DefaultSubscriptionDAO.getInstance().getSubscriptionById(id);
                if (subscription != null && subscription.isActive()) {
                    DefaultSubscriptionDAO.getInstance().changeSubscriptionToFalse(subscription);
                } if (!subscription.isActive()) {
                    DefaultSubscriptionDAO.getInstance().changeSubscriptionToTrue(subscription);
                }
                response.sendRedirect("/subscription?id=" + subscription.getAccountId());
            } catch (Exception ex) {
                response.sendRedirect("/error");
            }
        }
    }
}
