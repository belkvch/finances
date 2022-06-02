package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.entyti.Accounts;
import org.slf4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/updateAccount")
public class UpdateAccountServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAccountServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Accounts account = DefaultAccountDAO.getInstance().getAccountById(id);
        if (account != null) {
            try {
                BigDecimal bigDecimal = new BigDecimal(req.getParameter("amount"));
                if (bigDecimal.compareTo(BigDecimal.valueOf(0)) > 0) {
                    account.setAmount(account.getAmount().add(bigDecimal));
                    DefaultAccountDAO.getInstance().changeOperationAmount(account);
                } else {
                    LOGGER.info("compareTo wrong");
                    resp.sendRedirect("/error");
                }
            } catch (NumberFormatException e) {
                LOGGER.info("NumberFormatException in doPost in UpdateAccountServlet");
                resp.sendRedirect("/error");
            }
        }
        resp.sendRedirect("/accounts");
    }
}

