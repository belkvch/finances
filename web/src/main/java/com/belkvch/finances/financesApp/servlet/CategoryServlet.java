package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultCategoryDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.*;
import org.slf4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession(true);
        int userId = (int) httpSession.getAttribute("id");
        User currenUser = DefaultUserDAO.getInstance().getUserById(userId);
        Role roleBan = new Role("BAN");
        if (currenUser.getRoleId().getName().equals(roleBan.getName())) {
            req.getRequestDispatcher("/ban.jsp").forward(req, resp);
        } else {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                User user = DefaultUserDAO.getInstance().getUserByAccountId(id, userId);
                if (user.getId() == userId) {

                    List<Category> categories = DefaultCategoryDAO.getInstance().showCategoriesById(id);

                    List<Category> categoryList = new ArrayList<>();
                    req.setAttribute("categoryList", categoryList);
                    categoryList.add(new Category());

                    List<Accounts> accounts = new ArrayList<>();
                    accounts.add(new Accounts(id));
                    req.setAttribute("accounts", accounts);

                    req.setAttribute("categories", categories);
                    getServletContext().getRequestDispatcher("/category.jsp").forward(req, resp);
                }
            } catch (NumberFormatException | NullPointerException e) {
                LOGGER.info("NumberFormatException or NullPointerException in doGet in CategoryServlet");
                resp.sendRedirect("/error");
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        if ("create".equals(req.getParameter("actionType"))) {
            Category category = new Category();

            int account_id = Integer.parseInt(req.getParameter("id"));

            String name = req.getParameter("category_name");
            if (name == null || name.isEmpty() || name.trim().isEmpty()) {
                LOGGER.info("name for category is empty");
                resp.sendRedirect("/error");
            } else {
                category.setName(name);
                category.setNecessary(false);
                Category newCategory = DefaultCategoryDAO.getInstance().addNewCategory(category);
                DefaultCategoryDAO.getInstance().addCategoryAccountConn(newCategory, account_id);
            }

            resp.sendRedirect("/category?id=" + account_id);
        } else {
            LOGGER.info("actionType for doPost in CategoryServlet isn't create");
            resp.sendRedirect("/error");
        }
    }
}
