package com.belkvch.finances.financesApp.servlet;

import com.belkvch.finances.financesApp.dao.DefaultAccountDAO;
import com.belkvch.finances.financesApp.dao.DefaultCurrencyDAO;
import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.entyti.Accounts;
import com.belkvch.finances.financesApp.entyti.Category;
import com.belkvch.finances.financesApp.entyti.Currency;
import com.belkvch.finances.financesApp.entyti.Operations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int account_id = Integer.parseInt(req.getParameter("account_id"));
        int id = Integer.parseInt(req.getParameter("id"));
        Accounts fromAccount = DefaultAccountDAO.getInstance().getAccountById(id);
        Accounts toAccount = DefaultAccountDAO.getInstance().getAccountById(account_id);
        if (toAccount != null && fromAccount != null) {
            try {
                BigDecimal bigDecimal = new BigDecimal(req.getParameter("transfer_amount"));
                if (bigDecimal.compareTo(BigDecimal.valueOf(0)) > 0) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(formatter.format(new Date()));
                    Operations operationTransfer = new Operations("Transfer", date, bigDecimal, id, new Category(2));
                    if (fromAccount.getAmount().subtract(bigDecimal).compareTo(BigDecimal.valueOf(0)) > 0) {
                        fromAccount.setAmount(fromAccount.getAmount().subtract(bigDecimal));
                        DefaultAccountDAO.getInstance().changeOperationAmount(fromAccount);
                        Currency currencyToTransfer = DefaultCurrencyDAO.getInstance().findById(fromAccount.getCurrencyId().getId());
                        if (currencyToTransfer != null) {
                            if (toAccount.getCurrencyId().getId() == 1) {
                                BigDecimal result = bigDecimal.multiply(currencyToTransfer.getToByn());
                                toAccount.setAmount(toAccount.getAmount().add(result));
                                DefaultAccountDAO.getInstance().changeOperationAmount(toAccount);
                                DefaultOperationsDAO.getInstance().addNewOperation(operationTransfer);
                            } else if (toAccount.getCurrencyId().getId() == 2) {
                                BigDecimal result = bigDecimal.multiply(currencyToTransfer.getToEur());
                                toAccount.setAmount(toAccount.getAmount().add(result));
                                DefaultAccountDAO.getInstance().changeOperationAmount(toAccount);
                                DefaultOperationsDAO.getInstance().addNewOperation(operationTransfer);
                            } else if (toAccount.getCurrencyId().getId() == 3) {
                                BigDecimal result = bigDecimal.multiply(currencyToTransfer.getToUsd());
                                toAccount.setAmount(toAccount.getAmount().add(result));
                                DefaultAccountDAO.getInstance().changeOperationAmount(toAccount);
                                DefaultOperationsDAO.getInstance().addNewOperation(operationTransfer);
                            }
                        } else {
                            resp.sendRedirect("/error");
                        }
                    } else {
                        resp.sendRedirect("/error");
                    }
                } else {
                    resp.sendRedirect("/error");
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("/error");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            resp.sendRedirect("/error");
        }
        resp.sendRedirect("/accounts");
    }
}
