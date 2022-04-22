package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.dao.DefaultCategoryDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin-category")
public class AdminCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        int userId = (int) httpSession.getAttribute("id");
        User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
        Role roleAdmin = new Role("ADMIN");
        if (currenUser.getRoleId().getName().equals(roleAdmin.getName())) {
            List<Category> categories = DefaultCategoryDAO.getInstance().showAllCategoriesForAdmin();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/admin-category.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/accounts");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        if ("create".equals(req.getParameter("actionType"))) {
            Category category = new Category();

            String name = req.getParameter("categoryName");
            if (name == null || name.isEmpty() || name.trim().isEmpty()) {
                resp.sendRedirect("/error");
            } else {
                category.setName(name);
                category.setNecessary(true);
                DefaultCategoryDAO.getInstance().addNewCategory(category);
                List<Accounts> accounts = DefaultAccountDAO.getInstance().showAllAccounts();
                Category newCategory = DefaultCategoryDAO.getInstance().getLastCategory();
                for (Accounts account:accounts) {
                    DefaultCategoryDAO.getInstance().addCategoryAccountConn(newCategory,account.getId());
                }
            }

            resp.sendRedirect("/admin-category");
        } else {
            resp.sendRedirect("/error");
        }
    }
}
