package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultCurrencyDAO;
import com.belkvch.finances.financesApp.entyti.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencies = DefaultCurrencyDAO.getInstance().showAllCurrency();
        req.setAttribute("currencies", currencies);
        req.getRequestDispatcher("/currency.jsp").forward(req, resp);
    }
}
