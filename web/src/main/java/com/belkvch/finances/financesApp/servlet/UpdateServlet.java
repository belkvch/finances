package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.Operations;
import com.belkvch.finances.financesApp.entyti.Role;
import com.belkvch.finances.financesApp.entyti.User;

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
                if (operation != null) {
                    List<Operations> operations = new ArrayList<>();
                    operations.add(operation);
                    request.setAttribute("operations", operations);
                    getServletContext().getRequestDispatcher("/update.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/error");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("/error");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("update".equals(request.getParameter("actionType"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            Operations operation = DefaultOperationsDAO.getInstance().getOperationById(id);
            if (operation != null) {
                String name = request.getParameter("name");
                if (name == null || name.isEmpty() || name.trim().isEmpty()) {
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
                } catch (ParseException e) {
                    response.sendRedirect("/error");

                } catch (NumberFormatException e) {
                    response.sendRedirect("/error");
                }

                try {
                    BigDecimal bigDecimal = new BigDecimal(request.getParameter("salary"));
                    if (bigDecimal.compareTo(BigDecimal.valueOf(0)) > 0) {
                        operation.setPriceOfOperation(bigDecimal);
                        DefaultOperationsDAO.getInstance().changeOperationSalary(operation);
                    } else {
                        response.sendRedirect("/error");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("/error");
                }
            }
            response.sendRedirect("/operations?id=" + operation.getAccountId());
        } else {
            response.sendRedirect("/error");
        }
    }
}

