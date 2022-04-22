package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultCategoryDAO;
import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.entyti.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteCategoryAdmin")
public class DeleteCategoryForAdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int category_id = Integer.parseInt(request.getParameter("category_id"));
        Category category = DefaultCategoryDAO.getInstance().getCategoryById(category_id);
        if (category != null && category.isNecessary() && category.getId()!=1 && category.getId()!=2) {
            DefaultOperationsDAO.getInstance().changeOperationCategoryToNull(category_id);
            DefaultCategoryDAO.getInstance().deleteCategory(category);
        } else {
            response.sendRedirect("/error");
        }
        response.sendRedirect("/admin-category");
    }
}
