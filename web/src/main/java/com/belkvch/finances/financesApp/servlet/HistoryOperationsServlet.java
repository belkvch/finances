package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.dao.DefaultCategoryDAO;
import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/history")
public class HistoryOperationsServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryOperationsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        int userId = (int) httpSession.getAttribute("id");
        User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
        Role roleBan = new Role("BAN");
        if (currenUser.getRoleId().getName().equals(roleBan.getName())) {
            req.getRequestDispatcher("/ban.jsp").forward(req, resp);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            String dateFind = req.getParameter("date");
            User user = DefaultUserDAO.getInstance().getUserByAccountId(id, userId);
            try {
                if (user.getId() == userId) {

                    List<Accounts> accounts =new ArrayList<>();
                    Accounts account = DefaultAccountDAO.getInstance().getAccountById(id);
                    accounts.add(account);
                    req.setAttribute("accounts", accounts);

                    List<Category> categories = DefaultCategoryDAO.getInstance().showCategoriesById(id);
                    req.setAttribute("categories", categories);

                    List<Operations> operationsList = new ArrayList<>();
                    req.setAttribute("operationsList", operationsList);
                    operationsList.add(new Operations(id));
                    if (dateFind == null || dateFind.equals(" ") || dateFind.isEmpty()) {
                        List<Operations> operations = DefaultOperationsDAO.getInstance().showAllOperationsForHistory(id);
                        req.setAttribute("operations", operations);
                        getServletContext().getRequestDispatcher("/history.jsp").forward(req, resp);
                    } else {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            sdf.setLenient(false);
                            Date submitDate = sdf.parse(dateFind);
                            java.sql.Date dateSQL = new java.sql.Date(submitDate.getTime());
                            List<Operations> operations = DefaultOperationsDAO.getInstance().showAllOperationsForAccount(id, dateSQL);
                            req.setAttribute("operations", operations);
                            getServletContext().getRequestDispatcher("/history.jsp").forward(req, resp);
                        } catch (ParseException e) {
                            LOGGER.info("ParseException");
                        }
                    }
                }
            } catch (NullPointerException e) {
                LOGGER.info("NullPointerException in doGet in HistoryOperationsServlet");
                resp.sendRedirect("/error");
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        if ("find".equals(req.getParameter("actionType"))) {
            int account_id = Integer.parseInt(req.getParameter("id"));
            String dateFind = req.getParameter("date");
            if (dateFind == null || dateFind.equals(" ") || dateFind.isEmpty()) {
                resp.sendRedirect("/history?id=" + account_id + "&date=");
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    Date submitDate = sdf.parse(dateFind);
                    java.sql.Date dateSQL = new java.sql.Date(submitDate.getTime());
                    resp.sendRedirect("/history?id=" + account_id + "&date=" + dateSQL);
                } catch (ParseException e) {
                    LOGGER.info("ParseException");
                    resp.sendRedirect("/error");
                }
            }
        } else {
            LOGGER.info("actionType isn't find");
            resp.sendRedirect("/error");
        }
    }
}
