package com.belkvch.finances.view;

import com.belkvch.finances.entyti.Operations;

public class OperationsView {
    public OperationsView() {
    }

    public void showOperationDetails(Operations operation) {
        System.out.println("The information about operation with id = " + operation.getId());
        System.out.println("Operation: " + operation.getNameOfOperation());
        System.out.println("Date: " + operation.getDateOfOperation().getTime());
        System.out.println("Price: " + operation.getPriceOfOperation());
    }
}