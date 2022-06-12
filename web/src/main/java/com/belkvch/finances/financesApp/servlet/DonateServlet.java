package com.belkvch.finances.financesApp.servlet;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.math.*;

@WebServlet("/donate")
public class DonateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BigDecimal dollars = BigDecimal.valueOf(Long.parseLong(req.getParameter("charge")));
//        req.getParameter("charge");
//        req.getParameter("currency");
        String currency = req.getParameter("currency");
        System.out.println(dollars);
        System.out.println(currency);
    }
}
