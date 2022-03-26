package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.entyti.Operations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Operations operation = DefaultOperationsDAO.getInstance().getOperationById(id);
        if (operation != null) {
            request.setAttribute("operation", operation);
            getServletContext().getRequestDispatcher("/update.jsp").forward(request, response);
        } else {
            response.sendRedirect("/operations");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("update".equals(request.getParameter("actionType"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            Operations operation = DefaultOperationsDAO.getInstance().getOperationById(id);
            if (operation != null) {
                String name = request.getParameter("name");
                operation.setNameOfOperation(name);
                DefaultOperationsDAO.getInstance().changeOperationName(operation);

                String date = request.getParameter("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                try {
                    Date submitDate = sdf.parse(date);
                    operation.setDateOfOperation(submitDate);
                    DefaultOperationsDAO.getInstance().changeOperationDate(operation);
                } catch (ParseException e) {
                    e.printStackTrace();

                } catch (NumberFormatException e) {
                    System.out.println(e);
                }

                try {
                    BigDecimal bigDecimal = new BigDecimal(request.getParameter("salary"));
                    if (bigDecimal.compareTo(BigDecimal.valueOf(0)) == 0) {
                        operation.setPriceOfOperation(bigDecimal);
                        DefaultOperationsDAO.getInstance().changeOperationSalary(operation);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }
        } else {
            System.out.println("Some another actionType");
        }
        response.sendRedirect("/operations");
    }
}

