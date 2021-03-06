package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.dao.DefaultCategoryDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.*;
import org.slf4j.*;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServlet.class);

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
            List<Accounts> accountsList = DefaultAccountDAO.getInstance().showAllAccountsForUser(userId);
            req.setAttribute("accountsList", accountsList);
            req.getRequestDispatcher("/accounts.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("create".equals(req.getParameter("actionType"))) {
            Accounts account = new Accounts();
            HttpSession httpSession = req.getSession(true);
            int userId = (int) httpSession.getAttribute("id");
            try {
                int withUserId = Integer.parseInt(req.getParameter("with_user_id"));
                int currencyId = Integer.parseInt(req.getParameter("currency_id"));
                if (currencyId == 0) {
                    LOGGER.info("currencyId is 0");
                    resp.sendRedirect("/error");
                } else {
                    account.setCurrencyId(new Currency(currencyId));
                }

                BigDecimal bigDecimal = new BigDecimal(req.getParameter("amount"));
                if (bigDecimal.compareTo(BigDecimal.valueOf(0)) > 0) {
                    account.setAmount(bigDecimal);
                } else {
                    LOGGER.info("compareTo wrong");
                    resp.sendRedirect("/error");
                }

                if (withUserId == 0) {
                    Accounts newAccount = DefaultAccountDAO.getInstance().addNewAccount(account);
                    DefaultAccountDAO.getInstance().addUserAccountConn(newAccount, userId);
                    List<Category> categories = DefaultCategoryDAO.getInstance().showAllCategories();
                    for (Category category : categories) {
                        if (category.isNecessary()) {
                            DefaultCategoryDAO.getInstance().addCategoryAccountConn(category, newAccount.getId());
                        }
                    }
                } else {
                    if (DefaultUserDAO.getInstance().getUserById(withUserId) != null && withUserId != userId) {
                        Accounts newAccount = DefaultAccountDAO.getInstance().addNewAccount(account);
                        DefaultAccountDAO.getInstance().addUserAccountConn(newAccount, userId);
                        DefaultAccountDAO.getInstance().addUserAccountConn(newAccount, withUserId);
                        List<Category> categories = DefaultCategoryDAO.getInstance().showAllCategories();
                        for (Category category : categories) {
                            if (category.isNecessary()) {
                                DefaultCategoryDAO.getInstance().addCategoryAccountConn(category, newAccount.getId());
                            }
                        }
                    } else {
                        LOGGER.info("wrong if in doPost in AccountServlet for make account for two users");
                        resp.sendRedirect("/error");
                    }
                }
            } catch (NumberFormatException e) {
                LOGGER.info("NumberFormatException for doPost in AccountServlet");
                resp.sendRedirect("/error");
            }
            resp.sendRedirect("/accounts");
        }
    }
}

