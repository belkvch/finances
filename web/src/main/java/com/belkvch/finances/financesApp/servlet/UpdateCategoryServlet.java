package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.dao.DefaultCategoryDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.Accounts;
import com.belkvch.finances.financesApp.entyti.Category;
import com.belkvch.finances.financesApp.entyti.Role;
import com.belkvch.finances.financesApp.entyti.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/updateCategory")
public class UpdateCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        int userId = (int) httpSession.getAttribute("id");
        User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
        Role roleBan = new Role("BAN");
        if (currenUser.getRoleId().getName().equals(roleBan.getName())) {
            req.getRequestDispatcher("/ban.jsp").forward(req, resp);
        } else {
                int id = Integer.parseInt(req.getParameter("id"));
                Accounts account = DefaultAccountDAO.getInstance().getAccountById(id);
                if (account.getUserId().getId() == userId) {
                    List<Accounts> accounts = new ArrayList<>();
                    accounts.add(new Accounts(id));
                    req.setAttribute("accounts", accounts);
                    resp.sendRedirect("/category?id=" + id);
                } else {
                    resp.sendRedirect("/error");
                }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int category_id = Integer.parseInt(req.getParameter("category_id"));
        Category category = DefaultCategoryDAO.getInstance().getCategoryById(category_id);
        if (category != null && !category.isNecessary()) {
            String name = req.getParameter("category_name");
            if (name == null || name.isEmpty() || name.trim().isEmpty()) {
                resp.sendRedirect("/error");
            } else {
                category.setName(name);
            }
            DefaultCategoryDAO.getInstance().changeCategoryName(category);
        } else {
            resp.sendRedirect("/error");
        }
        resp.sendRedirect("/category?id=" + id);
    }
}
