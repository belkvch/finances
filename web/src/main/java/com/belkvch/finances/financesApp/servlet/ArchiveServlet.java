package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.Accounts;
import com.belkvch.finances.financesApp.entyti.Role;
import com.belkvch.finances.financesApp.entyti.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/archive-account")
public class ArchiveServlet extends HttpServlet {
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
                User user = DefaultUserDAO.getInstance().getUserById(id);
                if (user.getId() == userId) {
                    List<Accounts> accounts = DefaultAccountDAO.getInstance().showAllAccountsForUserArchive(userId);
                    req.setAttribute("accounts", accounts);
                    req.getRequestDispatcher("/archive-account.jsp").forward(req, resp);
                } else resp.sendRedirect("/error");
            } catch (NullPointerException e) {
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
                Accounts account = DefaultAccountDAO.getInstance().getAccountById(id);
                if (account != null && account.isActiveAccount()) {
                    DefaultAccountDAO.getInstance().changeAccountStatus(account);
                }
                response.sendRedirect("/accounts");
            } catch (Exception ex) {
                response.sendRedirect("/error");
            }
        }
    }
}
