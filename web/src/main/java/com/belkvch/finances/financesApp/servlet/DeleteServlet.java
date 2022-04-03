package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.entyti.Operations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Operations operation = DefaultOperationsDAO.getInstance().getOperationById(id);
            DefaultOperationsDAO.getInstance().deleteOperation(operation);
            response.sendRedirect(request.getContextPath() + "/operations");
        } catch (Exception ex) {
            response.sendRedirect("/error");
        }
    }
}
