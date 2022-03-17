package com.belkvch.finances.service;

import com.belkvch.finances.entyti.Operations;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DefaultOperationsService implements OperationsService {
    private List<Operations> operationsList = new ArrayList();

    public DefaultOperationsService() {
        operationsList.add(new Operations(0, "Buy IPhone", Calendar.getInstance(), new BigDecimal("455656.545")));
        operationsList.add(new Operations(1, "Buy IPad", Calendar.getInstance(), new BigDecimal("3434.2")));
        operationsList.add(new Operations(2, "Buy MacBook", Calendar.getInstance(), new BigDecimal("432432")));
    }

    public List<Operations> getOperationsList() {
        return this.operationsList;
    }

    @Override
    public Operations getOperationById(int id) {
        Operations operationForGetById = operationsList.get(id);
        return operationForGetById;
    }

    @Override
    public Operations addNewOperation() throws IOException, ParseException {
        Scanner scannerName = new Scanner(System.in);
        System.out.println("Enter the name of operation: ");
        String newNameOfOperation = scannerName.nextLine();
        System.out.println("Enter the date of operation: ");
        String format = "MM/dd/yyyy";
        Scanner scannerDate = new Scanner(System.in);
        String input = scannerDate.nextLine();
        Date date = (new SimpleDateFormat("MM/dd/yyyy")).parse("12/10/1994");
        Calendar newDateOfOperation = Calendar.getInstance();
        newDateOfOperation.setTime(date);
        Scanner scannerSalary = new Scanner(System.in);
        System.out.println("Enter the price of operation: ");
        BigDecimal newPriceOfOperation = scannerSalary.nextBigDecimal();
        int id = operationsList.size();
        Operations newOperation = new Operations(id, newNameOfOperation, newDateOfOperation, newPriceOfOperation);
        return newOperation;
    }

    @Override
    public Operations deleteOperationById(Integer id) {
        Operations operation = operationsList.get(id);
        if (id != null) {
            operationsList.remove(id);
        } else {
            System.out.println("Incorrect id. There are " + operationsList.size() + " operations.");
        }
        return operation;
    }
}