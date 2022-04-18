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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/operations")
public class OperationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        int userId = (int) httpSession.getAttribute("id");
        User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
        Role roleBan = new Role("BAN");
        if (currenUser.getRoleId().getName().equals(roleBan.getName())) {
            req.getRequestDispatcher("/ban.jsp").forward(req, resp);
        } else {
            try {
                int id = Integer.parseInt(req.getParameter("id"));

                Accounts account = DefaultAccountDAO.getInstance().getAccountById(id);
                if (account.getUserId().getId() == userId) {
                    List<Category> categories = DefaultCategoryDAO.getInstance().showCategoriesById(id);
                    req.setAttribute("categories", categories);

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date today = new Date();
                    Date todayWithZeroTime = formatter.parse(formatter.format(today));
                    java.sql.Date date = new java.sql.Date(todayWithZeroTime.getTime());

                    List<Operations> operations = DefaultOperationsDAO.getInstance().showAllOperationsForAccount(id, date);
                    List<Operations> operationsList = new ArrayList<>();

                    req.setAttribute("operationsList", operationsList);
                    operationsList.add(new Operations(id));
                    req.setAttribute("operations", operations);
                    getServletContext().getRequestDispatcher("/operations.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("/error");
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("/error");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        if ("create".equals(req.getParameter("actionType"))) {
            Operations operation = new Operations();

            int account_id = Integer.parseInt(req.getParameter("id"));
            operation.setAccountId(account_id);

            String name = req.getParameter("name");
            if (name == null || name.isEmpty() || name.trim().isEmpty()) {
                resp.sendRedirect("/error");
            } else {
                operation.setNameOfOperation(name);
            }

            try {
                String date = req.getParameter("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date submitDate = sdf.parse(date);
                operation.setDateOfOperation(submitDate);
            } catch (ParseException e) {
                resp.sendRedirect("/error");
            } catch (NumberFormatException e) {
                resp.sendRedirect("/error");
            }

            int categoryId = Integer.parseInt(req.getParameter("category_id"));
            operation.setCategoryId(new Category(categoryId));

            try {
                BigDecimal bigDecimal = new BigDecimal(req.getParameter("salary"));
                if (bigDecimal.compareTo(BigDecimal.valueOf(0)) > 0) {
                    operation.setPriceOfOperation(bigDecimal);
                    Accounts account = DefaultAccountDAO.getInstance().getAccountById(operation.getAccountId());
                    BigDecimal subtract = account.getAmount().subtract(bigDecimal);
                    if (subtract.compareTo(BigDecimal.valueOf(0)) > 0 || subtract.compareTo(BigDecimal.valueOf(0)) == 0) {
                        account.setAmount(subtract);
                        DefaultAccountDAO.getInstance().changeOperationAmount(account);
                        DefaultOperationsDAO.getInstance().addNewOperation(operation);
                    } else {
                        resp.sendRedirect("/error");
                    }
                } else {
                    resp.sendRedirect("/error");
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("/error");
            }

            resp.sendRedirect("/operations?id=" + operation.getAccountId());
        } else {
            resp.sendRedirect("/error");
        }
    }
}
