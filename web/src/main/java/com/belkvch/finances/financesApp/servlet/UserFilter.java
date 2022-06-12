package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultUserDAO;
import com.belkvch.finances.financesApp.entyti.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession httpSession = httpServletRequest.getSession(true);
        String url = httpServletRequest.getRequestURI();
        String username = servletRequest.getParameter("login");
        String password = servletRequest.getParameter("password");
        User user = DefaultUserDAO.getInstance().getByLogin(username);

        if (httpSession.getAttribute("login") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (username != null && password != null && user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                httpSession.setAttribute("login", username);
                httpSession.setAttribute("id", DefaultUserDAO.getInstance().getByLogin(username).getId());
                httpSession.setAttribute("role", DefaultUserDAO.getInstance().getByLogin(username).getRoleId().getId());

                if (url.contains("/donate")) {
                    httpServletResponse.sendRedirect("/donate");
                    return;
                } else{
                    httpServletResponse.sendRedirect("/main");
                }

            }
        } else {
            if ("/login".equals(url) || "/registration".equals(url) || url.startsWith("/ws")) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
            httpServletRequest.getRequestDispatcher("/login.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
