package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.dao.DefaultCategoryDAO;
import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        int userId = (int) httpSession.getAttribute("id");
        User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
        Role roleBan = new Role("BAN");
        if (currenUser.getRoleId().getName().equals(roleBan.getName())) {
            req.getRequestDispatcher("/ban.jsp").forward(req, resp);
        } else {
            List<Accounts> accounts = DefaultAccountDAO.getInstance().showAllAccountsForUser(userId);
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("/accounts.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("create".equals(req.getParameter("actionType"))) {
            Accounts account = new Accounts();
            HttpSession httpSession = req.getSession(true);
            int userId = (int) httpSession.getAttribute("id");
            account.setUserId(new User(userId));

            int currencyId = Integer.parseInt(req.getParameter("currency_id"));
            if (currencyId == 0) {
                resp.sendRedirect("/error");
            } else {
                account.setCurrencyId(new Currency(currencyId));
            }
            try {
                BigDecimal bigDecimal = new BigDecimal(req.getParameter("amount"));
                if (bigDecimal.compareTo(BigDecimal.valueOf(0)) > 0) {
                    account.setAmount(bigDecimal);
                } else {
                    resp.sendRedirect("/error");
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("/error");
            }
            DefaultAccountDAO.getInstance().addNewAccount(account);
            Accounts newAccount = DefaultAccountDAO.getInstance().getLastAccount();
            DefaultAccountDAO.getInstance().getAccountCategoryConn(newAccount);
        } else {
            resp.sendRedirect("/error");
        }
    }
}

