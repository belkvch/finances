package com.belkvch.finances.service;

import com.belkvch.finances.entyti.Operations;
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


    @Override
    public Operations getOperationById(int id) {
        Operations operationForGetById = operationsList.get(id);
        return operationForGetById;
    }


    @Override
    public Operations addNewOperation() throws ParseException {
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
        operationsList.add(newOperation);
        return newOperation;
    }

    @Override
    public void deleteOperationById(int id) {
        Operations operation = operationsList.get(id);
        if (id < operationsList.size()) {
            operationsList.remove(id);
        } else {
            System.out.println("Incorrect id. There are " + operationsList.size() + " operations.");
        }
    }

    @Override
    public Operations changeOperationById(Integer id) throws ParseException {
        Operations operationChange = operationsList.get(id);

        Scanner scannerForChange = new Scanner(System.in);
        System.out.println("What do you want to change?");
        System.out.println("Enter 1 to change name");
        System.out.println("Enter 2 to change date");
        System.out.println("Enter 3 to change price");
        int changeOfOperation = scannerForChange.nextInt();

        switch (changeOfOperation) {
            case 1:
                Scanner scannerName = new Scanner(System.in);
                System.out.println("Enter the name of operation: ");
                String newNameOfOperation = scannerName.nextLine();
                operationChange.setNameOfOperation(newNameOfOperation);
                System.out.println("The name was changed");
                break;
            case 2:
                System.out.println("Enter the date of operation: ");
                String format = "MM/dd/yyyy";
                Scanner scannerDate = new Scanner(System.in);
                String input = scannerDate.nextLine();
                Date date = (new SimpleDateFormat("MM/dd/yyyy")).parse("12/10/1994");
                Calendar newDateOfOperation = Calendar.getInstance();
                newDateOfOperation.setTime(date);
                operationChange.setDateOfOperation(newDateOfOperation);
                System.out.println("The date was changed");
                break;
            case 3:
                Scanner scannerSalary = new Scanner(System.in);
                System.out.println("Enter the price of operation: ");
                BigDecimal newPriceOfOperation = scannerSalary.nextBigDecimal();
                operationChange.setPriceOfOperation(newPriceOfOperation);
                System.out.println("The price was changed");
                break;
            default:
                System.out.println("Enter 1-3 to change operation");
                break;
        }

        return operationChange;
    }

    @Override
    public List<Operations> showAllOperations() {
        return operationsList;
    }
}