package com.belkvch.finances.сontroller;

import java.text.ParseException;
import com.belkvch.finances.service.DefaultOperationsService;
import com.belkvch.finances.view.OperationsView;

public class OperationsController {
    private final DefaultOperationsService defaultOperationsService;
    private final OperationsView operationsView;

    public OperationsController(DefaultOperationsService defaultOperationsService, OperationsView operationsView) {
        this.defaultOperationsService = defaultOperationsService;
        this.operationsView = operationsView;
    }

    public void operationInfo(int id) {
        operationsView.showOperationDetails(defaultOperationsService.getOperationById(id));
    }

    public void addNewOperation() throws ParseException {
        operationsView.showOperationDetails(defaultOperationsService.addNewOperation());
    }

    public void deleteOperation(int id) {
        defaultOperationsService.deleteOperationById(id);
    }

    public void updateOperation(int id) throws ParseException {
        defaultOperationsService.changeOperationById(id);
    }

    public void showAllOperations() {
        operationsView.showAllOperationsDetails(defaultOperationsService.showAllOperations());
    }
}
