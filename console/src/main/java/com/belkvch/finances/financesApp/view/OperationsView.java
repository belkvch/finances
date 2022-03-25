package com.belkvch.finances.financesApp.view;

import com.belkvch.finances.financesApp.entyti.Operations;

import java.text.SimpleDateFormat;
import java.util.List;

public class OperationsView {
    public OperationsView() {
    }

    public void showOperationDetails(Operations operation) {
        System.out.println("The information about operation with id = " + operation.getId());
        System.out.println("Operation: " + operation.getNameOfOperation());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate= dateFormat.format(operation.getDateOfOperation().getTime());
        System.out.println("Date: " + stringDate);
        System.out.println("Price: " + operation.getPriceOfOperation().abs());
    }

    public void showAllOperationsDetails(List<Operations> operationsList){
        for (Operations operation:operationsList) {
            showOperationDetails(operation);
        }
    }
}