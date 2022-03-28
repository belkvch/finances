package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.entyti.Operations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OperationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Operations> operations = DefaultOperationsDAO.getInstance().showAllOperations();
        req.setAttribute("operations", operations);
        req.getRequestDispatcher("/operations.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("create".equals(req.getParameter("actionType"))) {
            Operations operation = new Operations();
            operation.setNameOfOperation(req.getParameter("name"));
            try {
                String date = req.getParameter("date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date submitDate = sdf.parse(date);
                operation.setDateOfOperation(submitDate);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
            try {
                BigDecimal bigDecimal = new BigDecimal(req.getParameter("salary"));
                if (bigDecimal.compareTo(BigDecimal.valueOf(0)) > 0) {
                    operation.setPriceOfOperation(bigDecimal);
                }
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
            DefaultOperationsDAO.getInstance().addNewOperation(operation);
        } else {
            System.out.println("Some another actionType");
        }
        resp.sendRedirect("/operations");
    }
}
