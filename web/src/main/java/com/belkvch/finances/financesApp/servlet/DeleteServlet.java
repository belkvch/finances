package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.Accounts;
import com.belkvch.finances.financesApp.entyti.Operations;
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

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteServlet.class);

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
                Operations operation = DefaultOperationsDAO.getInstance().getOperationById(id);
                if (operation.getCategoryId().getId() != 2) {
                    Accounts account = DefaultAccountDAO.getInstance().getAccountById(operation.getAccountId());
                    if (account.isActiveAccount()) {
                        account.setAmount(operation.getPriceOfOperation().add(account.getAmount()));
                        DefaultAccountDAO.getInstance().changeOperationAmount(account);
                        DefaultOperationsDAO.getInstance().deleteOperation(operation);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/operations?id=" + operation.getAccountId());
            } catch (Exception ex) {
                LOGGER.info("Exception in DeleteServlet");
                response.sendRedirect("/error");
            }
        }
    }
}
