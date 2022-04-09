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
import java.util.List;

@WebServlet("/operations")
public class OperationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            List<Operations> operations = DefaultOperationsDAO.getInstance().showAllOperationsForAccount(id);
            if (operations != null) {
                req.setAttribute("operations", operations);
                getServletContext().getRequestDispatcher("/operations.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("/error");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("/error");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("create".equals(req.getParameter("actionType"))) {
            Operations operation = new Operations();

            int account_id = Integer.parseInt(req.getParameter("id").trim());
            operation.setAccountId(account_id);

            String name = req.getParameter("name");
            if (name == null|| name.isEmpty() || name.trim().isEmpty()) {
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

            try {
                BigDecimal bigDecimal = new BigDecimal(req.getParameter("salary"));
                if (bigDecimal.compareTo(BigDecimal.valueOf(0)) > 0) {
                    operation.setPriceOfOperation(bigDecimal);
                } else {
                    resp.sendRedirect("/error");
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("/error");
            }

            DefaultOperationsDAO.getInstance().addNewOperation(operation);
        } else {
            resp.sendRedirect("/error");
        }
        resp.sendRedirect("/operations");
    }
}
