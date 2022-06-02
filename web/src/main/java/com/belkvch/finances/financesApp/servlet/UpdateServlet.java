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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                User user = DefaultUserDAO.getInstance().getUserByOnlyAccountId(operation.getAccountId());
                if (user.getId() == currenUser.getId()) {
                    List<Category> categories = DefaultCategoryDAO.getInstance().showCategoriesById(operation.getAccountId());
                    request.setAttribute("categories", categories);

                    List<Operations> operations = new ArrayList<>();
                    operations.add(operation);
                    request.setAttribute("operations", operations);
                    getServletContext().getRequestDispatcher("/update.jsp").forward(request, response);
                } else {
                    LOGGER.info("users don't match");
                    response.sendRedirect("/error");
                }
            } catch (NumberFormatException e) {
                LOGGER.info("NumberFormatException in doGet in UpdateServlet");
                response.sendRedirect("/error");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("update".equals(request.getParameter("actionType"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            Operations operation = DefaultOperationsDAO.getInstance().getOperationById(id);
            Accounts accountCheck = DefaultAccountDAO.getInstance().getAccountById(operation.getAccountId());
            if (accountCheck.isActiveAccount()) {
                if (operation.getCategoryId().getId() != 2) {
                    String name = request.getParameter("name");
                    if (name == null || name.isEmpty() || name.trim().isEmpty()) {
                        LOGGER.info("name is empty");
                        response.sendRedirect("/error");
                    } else {
                        operation.setNameOfOperation(name);
                        DefaultOperationsDAO.getInstance().changeOperationName(operation);
                    }

                    String date = request.getParameter("date");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    try {
                        Date submitDate = sdf.parse(date);
                        operation.setDateOfOperation(submitDate);
                        DefaultOperationsDAO.getInstance().changeOperationDate(operation);
                    } catch (ParseException | NumberFormatException e) {
                        LOGGER.info("ParseException or NumberFormatException in UpdateServlet");
                        response.sendRedirect("/error");
                    }

                    try {
                        BigDecimal bigDecimal = new BigDecimal(request.getParameter("salary"));
                        if (bigDecimal.compareTo(BigDecimal.valueOf(0)) > 0) {
                            if (bigDecimal.compareTo(operation.getPriceOfOperation()) > 0) {
                                BigDecimal difference = bigDecimal.subtract(operation.getPriceOfOperation());
                                Accounts account = DefaultAccountDAO.getInstance().getAccountById(operation.getAccountId());
                                BigDecimal differenceAccount = account.getAmount().subtract(difference);
                                account.setAmount(differenceAccount);
                                DefaultAccountDAO.getInstance().changeOperationAmount(account);
                                operation.setPriceOfOperation(bigDecimal);
                                DefaultOperationsDAO.getInstance().changeOperationSalary(operation);
                            } else if (bigDecimal.compareTo(operation.getPriceOfOperation()) < 0) {
                                BigDecimal difference = operation.getPriceOfOperation().subtract(bigDecimal);
                                Accounts account = DefaultAccountDAO.getInstance().getAccountById(operation.getAccountId());
                                BigDecimal differenceAccount = account.getAmount().add(difference);
                                account.setAmount(differenceAccount);
                                DefaultAccountDAO.getInstance().changeOperationAmount(account);
                                operation.setPriceOfOperation(bigDecimal);
                                DefaultOperationsDAO.getInstance().changeOperationSalary(operation);
                            } else {
                                operation.setPriceOfOperation(bigDecimal);
                                DefaultOperationsDAO.getInstance().changeOperationSalary(operation);
                            }
                        } else {
                            LOGGER.info("wrong compareTo");
                            response.sendRedirect("/error");
                        }
                    } catch (NumberFormatException e) {
                        LOGGER.info("NumberFormatException in doPost in UpdateServlet ");
                        response.sendRedirect("/error");
                    }

                    int categoryId = Integer.parseInt(request.getParameter("category_id"));
                    operation.setCategoryId(new Category(categoryId));
                    DefaultOperationsDAO.getInstance().changeOperationCategory(operation);
                }
            }
            response.sendRedirect("/operations?id=" + operation.getAccountId());
        } else {
            LOGGER.info("actionType isn't change");
            response.sendRedirect("/error");
        }
    }
}

