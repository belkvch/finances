package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.dao.DefaultCategoryDAO;
import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/deleteCategory")
public class DeleteCategoryServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        int userId = (int) httpSession.getAttribute("id");
        User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
        Role roleBan = new Role("BAN");
        if (currenUser.getRoleId().getName().equals(roleBan.getName())) {
            request.getRequestDispatcher("/ban.jsp").forward(request, response);
        } else {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int category_id = Integer.parseInt(request.getParameter("category_id"));
                Category category = DefaultCategoryDAO.getInstance().getCategoryById(category_id);
                if (category != null && !category.isNecessary()) {
                    DefaultOperationsDAO.getInstance().changeOperationCategoryToNull(category_id);
                    DefaultCategoryDAO.getInstance().deleteCategory(category);
                }
                response.sendRedirect("/category?id=" + id);
            } catch (Exception ex) {
                response.sendRedirect("/error");
            }
        }
    }
}
