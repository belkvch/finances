package com.belkvch.finances.сontroller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.belkvch.finances.entyti.Operations;
import com.belkvch.finances.service.DefaultOperationsService;
import com.belkvch.finances.service.OperationsService;
import com.belkvch.finances.view.OperationsView;

public class OperationsController {
    private final DefaultOperationsService defaultOperationsService;
    private final OperationsView operationsView;

    public OperationsController(DefaultOperationsService defaultOperationsService, OperationsView operationsView) {
        this.defaultOperationsService = defaultOperationsService;
        this.operationsView = operationsView;
    }

    private OperationsService operationsService = DefaultOperationsService.getInstance();

    public void controllerRun() throws NullPointerException, InputMismatchException, ParseException {
        System.out.println("Welcome, user!");

        boolean isProgrammLive = true;
        while (isProgrammLive) {
            System.out.println("Enter what you want to do: ");
            System.out.println("1. Find operation by ID");
            System.out.println("2. Find operation by name");
            System.out.println("3. Add new operation");
            System.out.println("4. Choose operation to remove");
            System.out.println("5. Choose operation to update");
            System.out.println("6. Show all operations");
            System.out.println("7. Exit");

            Scanner scannerInterfaceUser = new Scanner(System.in);
            String interfaceUser = scannerInterfaceUser.next();

            switch (interfaceUser) {
//        Task 9 (Find operation by ID)
                case "1":
                    Scanner findInt = new Scanner(System.in);
                    System.out.println("Find operation by ID: ");
                    String scannerIdFind = findInt.next();
                    if (isInt(scannerIdFind)) {
                        int id = Integer.parseInt(scannerIdFind);
                        if (operationsService.getOperationById(id) == null) {
                            System.out.println("Operation with this id wasn't exist");
                            break;
                        } else
                            operationsView.showOperationDetails(operationsService.getOperationById(id));
                        break;
                    } else {
                        System.out.println("Incorrect value, try one more time:");
                        break;
                    }

//        Task 9 (Find operation by name)
                case "2":
                    Scanner findString = new Scanner(System.in);
                    System.out.println("Find operation by name: ");
                    String scannerString = findString.next();
                    if (operationsService.getOperationByName(scannerString) == null) {
                        System.out.println("Operation with this name wasn't exist");
                        break;
                    } else
                        operationsView.showOperationDetails(operationsService.getOperationByName(scannerString));
                    break;


                case "3":
//        Task 9 (Add operation)
                    System.out.println("Add new operation: ");
                    Operations operation = new Operations();

                    Scanner name = new Scanner(System.in);
                    System.out.println("Enter the name of new operation:");
                    operation.setNameOfOperation(name.next());

                    try {
                        System.out.println("Enter the date of operation with form *yyyy-MM-dd*: ");
                        Scanner scannerDate = new Scanner(System.in);
                        String date = scannerDate.next();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        dateFormat.setLenient(false);
                        Date date2 = dateFormat.parse(date);
                        operation.setDateOfOperation(date2);
                    } catch (ParseException parseException) {
                        System.out.println("Error with date");
                        break;
                    }

                    Scanner scannerSalary = new Scanner(System.in);
                    System.out.println("Enter the price of operation: ");
                    BigDecimal newPriceOfOperation = scannerSalary.nextBigDecimal();
                    operation.setPriceOfOperation(newPriceOfOperation.abs());
                    operationsService.addNewOperation(operation);
                    System.out.print("Operation was added.");

                    System.out.println();
                    break;

                case "4":
//        Task 10 (Delete operation)
                    Scanner deleteOperation = new Scanner(System.in);
                    System.out.println("Choose the id of operation to remove. ");
                    String scannerDelete = deleteOperation.next();
                    int id = 0;
                    if (isInt(scannerDelete)) {
                        id = Integer.parseInt(scannerDelete);
                    } else {
                        System.out.println("Incorrect id");
                        break;
                    }
                    if (operationsService.getOperationById(id) != null) {
                        operationsService.deleteOperation(operationsService.getOperationById(id));
                        System.out.println("This operation was deleted.");
                        break;
                    } else {
                        System.out.println("This operation wasn't deleted.");
                        break;
                    }


                case "5":
//        Task 10 (Update operation)
                    Operations operationUpdate = new Operations();
                    Scanner change = new Scanner(System.in);
                    System.out.println("Enter the id of operation to update: ");
                    String scannerIdToChange = change.next();
                    if (isInt(scannerIdToChange)) {
                        if (operationsService.getOperationById(Integer.parseInt(scannerIdToChange)) == null) {
                            System.out.println("Incorrect id");
                            break;
                        } else {
                            operationUpdate.setId(Integer.parseInt(scannerIdToChange));
                        }
                    } else {
                        System.out.println("Incorrect");
                    }

                    System.out.println("You want to update:");
                    operationsView.showOperationDetails(operationsService.getOperationById(operationUpdate.getId()));

                    Scanner scannerForChange = new Scanner(System.in);
                    System.out.println("What do you want to change?");
                    System.out.println("Enter 1 to change name");
                    System.out.println("Enter 2 to change date");
                    System.out.println("Enter 3 to change price");
                    String  changeOfOperation = scannerForChange.next();

                    switch (changeOfOperation) {
                        case "1":
                            Scanner scannerName = new Scanner(System.in);
                            System.out.println("Enter the name of operation: ");
                            String newNameOfOperation = scannerName.nextLine();
                            operationUpdate.setNameOfOperation(newNameOfOperation);
                            System.out.println("The name was changed");
                            operationsService.changeOperation(operationUpdate);
                            break;
                        case "2":
                            try {
                                System.out.println("Enter the date of operation with form *yyyy-MM-dd*: ");
                                Scanner scannerDate = new Scanner(System.in);
                                String date = scannerDate.next();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                dateFormat.setLenient(false);
                                Date date2 = dateFormat.parse(date);
                                operationUpdate.setDateOfOperation(date2);
                                System.out.println("The date was changed");
                                operationsService.changeOperation(operationUpdate);
                            } catch (ParseException parseException) {
                                System.out.println("Error with date");
                            } finally {
                                break;
                            }
                        case "3":
                            Scanner scannerSalaryUpdate = new Scanner(System.in);
                            System.out.println("Enter the price of operation: ");
                            BigDecimal newPriceOfOperationUpdate = scannerSalaryUpdate.nextBigDecimal();
                            operationUpdate.setPriceOfOperation(newPriceOfOperationUpdate);
                            System.out.println("The price was changed");
                            operationsService.changeOperation(operationUpdate);
                            break;
                        default:
                            System.out.println("Enter 1-3 to change operation");
                            break;
                    }
                    break;

//        Task 9 (Show all operations)
                case "6":
                    System.out.println("All operations:");
                    operationsView.showAllOperationsDetails(defaultOperationsService.showAllOperations());
                    break;

                case "7":
                    isProgrammLive = false;
                    break;

                default:
                    System.out.println("Enter the number from 1 - 6.");
            }
        }
    }


    private static boolean isInt(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}