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

            String dateString = req.getParameter("date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date submitDate = null;
            try {
                submitDate = sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            operation.setDateOfOperation(submitDate);

            BigDecimal bigDecimal = new BigDecimal(req.getParameter("salary"));
            operation.setPriceOfOperation(bigDecimal);

            DefaultOperationsDAO.getInstance().addNewOperation(operation);
        } else {
            System.out.println("some another actionType");
        }
        resp.sendRedirect("/operations");
    }

}
