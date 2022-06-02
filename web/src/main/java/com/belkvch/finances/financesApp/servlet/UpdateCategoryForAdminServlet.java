package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultCategoryDAO;
import com.belkvch.finances.financesApp.entyti.Category;
import org.slf4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateCategoryAdmin")
public class UpdateCategoryForAdminServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCategoryForAdminServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int category_id = Integer.parseInt(req.getParameter("category_id"));
        Category category = DefaultCategoryDAO.getInstance().getCategoryById(category_id);
        if (category != null && category.isNecessary() && category.getId()!=1 && category.getId()!=2) {
            String name = req.getParameter("category_name");
            if (name == null || name.isEmpty() || name.trim().isEmpty()) {
                LOGGER.info("name is empty");
                resp.sendRedirect("/error");
            } else {
                category.setName(name);
            }
            DefaultCategoryDAO.getInstance().changeCategoryName(category);
        } else {
            LOGGER.info("admin can't change that category");
            resp.sendRedirect("/error");
        }
        resp.sendRedirect("/admin-category");
    }
}
