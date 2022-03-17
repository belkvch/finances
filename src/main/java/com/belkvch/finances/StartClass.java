package com.belkvch.finances;

import com.belkvch.finances.view.OperationsView;
import com.belkvch.finances.сontroller.OperationsController;
import com.belkvch.finances.service.DefaultOperationsService;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StartClass {
    public static void main(String[] args) throws ParseException {
        DefaultOperationsService defaultOperationsService = new DefaultOperationsService();
        OperationsView operationsView = new OperationsView();
        OperationsController operationsController = new OperationsController(defaultOperationsService, operationsView);

        System.out.println("Welcome, user!");

        boolean isProgrammLive = true;
        while (isProgrammLive) {
            System.out.println("Enter what you want to do: ");
            System.out.println("1. Find operation by ID");
            System.out.println("2. Add new operation");
            System.out.println("3. Choose operation to remove");
            System.out.println("4. Choose operation to update");
            System.out.println("5. Show all operations");
            System.out.println("6. Exit");

            try {
                Scanner scannerInterfaceUser = new Scanner(System.in);
                int interfaceUser = scannerInterfaceUser.nextInt();

                switch (interfaceUser) {
                    case 1:
//        Task 2 (Find operation)
                        try {
                            Scanner findInt = new Scanner(System.in);
                            System.out.println("Find operation by ID: ");
                            int scannerIdFind = findInt.nextInt();
                            operationsController.operationInfo(scannerIdFind);
                            System.out.println();
                        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                            System.out.println("There is no operation with this id");
                        } finally {
                            break;
                        }

                    case 2:
//        Task 3 (Add operation)
                        try {
                            System.out.println("Add new operation: ");
                            operationsController.addNewOperation();
                            System.out.println();
                        } catch (NullPointerException nullPointerException) {
                            System.out.println("Wrong application work");
                        } finally {
                            break;
                        }

                    case 3:
//        Task 5 (Delete operation)
                        try {
                            Scanner deleteInt = new Scanner(System.in);
                            System.out.println("Choose the id of operation to remove: ");
                            int scannerIdDelete = deleteInt.nextInt();
                            operationsController.operationInfo(scannerIdDelete);
                            System.out.println();
                            Scanner deleteChooseText = new Scanner(System.in);
                            System.out.println("Write *Yes* if you want to delete this operation");
                            String scannerChooseTextDelete = deleteChooseText.nextLine();
                            if (scannerChooseTextDelete.equalsIgnoreCase("Yes")) {
                                operationsController.deleteOperation(scannerIdDelete);
                                System.out.println("This operation was deleted.");
                            } else {
                                System.out.println("This operation wasn't deleted.");
                            }
                            System.out.println();
                        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                            System.out.println("There is no option with this id");
                        } finally {
                            break;
                        }

                    case 4:
//        Task 4 (Update operation)
                        try {
                            Scanner changeInt = new Scanner(System.in);
                            System.out.println("Enter the id of operation to update: ");
                            int scannerIdToChange = changeInt.nextInt();
                            operationsController.operationInfo(scannerIdToChange);
                            operationsController.updateOperation(scannerIdToChange);
                            System.out.println();
                        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                            System.out.println("There is no operation with this id");
                        } finally {
                            break;
                        }

                    case 5:
                        //    Task 6 (show all operations)
                        operationsController.showAllOperations();
                        System.out.println();
                        break;
                    case 6:
                        isProgrammLive = false;
                        break;
                    default:
                        System.out.println("Enter the number from 1 - 6.");
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Wrong type of text.");
            }
        }
    }
}
