package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultSubscriptionDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.*;
import org.slf4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete-timer")
public class DeleteSubscriptionServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSubscriptionServlet.class);

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
                DefaultSubscriptionDAO.getInstance().deleteSubscription(subscription);
                response.sendRedirect("/subscription?id=" + subscription.getAccountId());
            } catch (Exception ex) {
                LOGGER.info("Exception in DeleteSubscriptionServlet");
                response.sendRedirect("/error");
            }
        }
    }
}
