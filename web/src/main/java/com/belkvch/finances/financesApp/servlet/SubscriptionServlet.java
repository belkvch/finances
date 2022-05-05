package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.*;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@WebServlet("/subscription")
public class SubscriptionServlet extends HttpServlet {
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
                User user = DefaultUserDAO.getInstance().getUserByAccountId(id, userId);
                if (user.getId() == userId) {

                    List<Category> categories = DefaultCategoryDAO.getInstance().showCategoriesById(id);
                    req.setAttribute("categories", categories);

                    List<Subscription> subscriptions = DefaultSubscriptionDAO.getInstance().showAllSubscriptionsForAccount(id);
                    req.setAttribute("subscriptions", subscriptions);

                    List<Operations> operationsList = new ArrayList<>();
                    req.setAttribute("operationsList", operationsList);
                    operationsList.add(new Operations(id));

                    getServletContext().getRequestDispatcher("/subscription.jsp").forward(req, resp);
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("/error");
            } catch (NullPointerException e) {
                resp.sendRedirect("/error");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        if ("create".equals(req.getParameter("actionType"))) {
            Subscription subscription = new Subscription();
            int account_id = Integer.parseInt(req.getParameter("id"));
            subscription.setAccountId(account_id);
            String name = req.getParameter("name");
            if (name == null || name.isEmpty() || name.trim().isEmpty()) {
                resp.sendRedirect("/error");
            } else {
                subscription.setName(name);
            }
            subscription.setActive(true);


            Operations operation = new Operations();
            operation.setAccountId(account_id);
            operation.setNameOfOperation(name);
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                Date todayWithZeroTime = formatter.parse(formatter.format(today));
                java.sql.Date date = new java.sql.Date(todayWithZeroTime.getTime());
                operation.setDateOfOperation(date);
            } catch (ParseException e) {
                e.printStackTrace();
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
                        Operations operationNew = DefaultOperationsDAO.getInstance().addNewOperation(operation);
                        subscription.setOperation(operationNew);
                        DefaultSubscriptionDAO.getInstance().addNewSubscription(subscription);
                    } else {
                        resp.sendRedirect("/error");
                    }
                } else {
                    resp.sendRedirect("/error");
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("/error");
            }

            ScheduledExecutorService sesService = Executors.newSingleThreadScheduledExecutor();
            Runnable sec = new Runnable() {
                @Override
                public void run() {
                    List <Subscription> subscriptions = DefaultSubscriptionDAO.getInstance().showAllSubscriptions();
                    for (Subscription sub: subscriptions) {
                        if (sub.isActive()) {
                            Operations operation = DefaultOperationsDAO.getInstance().getOperationById(sub.getOperation().getId());
                            BigDecimal amount = operation.getPriceOfOperation();
                            Accounts account = DefaultAccountDAO.getInstance().getAccountById(sub.getAccountId());
                            BigDecimal subtract = account.getAmount().subtract(amount);
                            if (subtract.compareTo(BigDecimal.valueOf(0)) > 0 || subtract.compareTo(BigDecimal.valueOf(0)) == 0) {
                                account.setAmount(subtract);
                                DefaultAccountDAO.getInstance().changeOperationAmount(account);
                                DefaultOperationsDAO.getInstance().addNewOperation(operation);
                            } else {
                                DefaultSubscriptionDAO.getInstance().changeSubscriptionToFalse(sub);
                            }
                        }
                    }
                }
            };
            ScheduledFuture<?> scheduledFuture = sesService.scheduleAtFixedRate(sec, 10, 10, TimeUnit.SECONDS);

            resp.sendRedirect("/subscription?id=" + subscription.getAccountId());
        } else {
            resp.sendRedirect("/error");
        }

    }

}

