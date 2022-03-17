package com.belkvch.finances;

import com.belkvch.finances.view.OperationsView;
import com.belkvch.finances.сontroller.OperationsController;
import com.belkvch.finances.service.DefaultOperationsService;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class StartClass {
    public static void main(String[] args) throws IOException, ParseException {
        DefaultOperationsService defaultOperationsService = new DefaultOperationsService();
        OperationsView operationsView = new OperationsView();
        OperationsController operationsController = new OperationsController(defaultOperationsService, operationsView);

//        Task 2
        Scanner findInt = new Scanner(System.in);
        System.out.println("Find operation by ID: ");
        int scannerIdFind = findInt.nextInt();
        operationsController.operationInfo(scannerIdFind);

//        Task 3
        System.out.println("Add new operation: ");
        operationsController.addNewOperation();

//        Task 4
        Scanner deleteInt = new Scanner(System.in);
        System.out.println("Choose the operation to remove: ");
        int scannerIdDelete = deleteInt.nextInt();
        operationsController.operationInfo(scannerIdDelete);
        System.out.println();
        Scanner deleteChooseText = new Scanner(System.in);
        System.out.println("Write *Yes* if you want to delete this operation");
        String scannerChooseTextDelete = deleteChooseText.nextLine();
        if (scannerChooseTextDelete.equals("Yes")) {
            operationsController.deleteOperation(scannerIdDelete);
            System.out.println("This operation was deleted.");
        } else {
            System.out.println("This operation wasn't deleted.");
        }
    }
}