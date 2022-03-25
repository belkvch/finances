package com.belkvch.finances.financesApp;

import com.belkvch.finances.financesApp.view.OperationsView;
import com.belkvch.finances.financesApp.сontroller.OperationsController;
import com.belkvch.finances.financesApp.service.DefaultOperationsService;

import java.text.ParseException;
import java.util.InputMismatchException;

public class StartClass {
    public static void main(String[] args) throws ParseException {
        try {
            DefaultOperationsService defaultOperationsService = new DefaultOperationsService();
            OperationsView operationsView = new OperationsView();
            OperationsController operationsController = new OperationsController(defaultOperationsService, operationsView);
            operationsController.controllerRun();
        } catch (NullPointerException e) {
            System.out.println("Null value");
        } catch (InputMismatchException e) {
            System.out.println("Uncorrect input");
        }
    }
}
