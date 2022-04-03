package com.belkvch.finances.financesApp.service;

import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
import com.belkvch.finances.financesApp.dao.OperationsDAO;
import com.belkvch.finances.financesApp.entyti.Operations;


import java.util.List;


public class DefaultOperationsService implements OperationsService {
    private OperationsDAO operationsDAO = DefaultOperationsDAO.getInstance();

    private static volatile DefaultOperationsService operationsService;

    public DefaultOperationsService() {
    }

    public static DefaultOperationsService getInstance() {
        if (operationsService == null) {
            synchronized (DefaultOperationsService.class) {
                if (operationsService == null) {
                    operationsService = new DefaultOperationsService();
                }
            }

        }
        return operationsService;
    }

    @Override
    public Operations getOperationById(int id)  {
        return operationsDAO.getOperationById(id);
    }

    @Override
    public Operations getOperationByName(String name)  {
        return operationsDAO.getOperationByName(name);
    }

    @Override
    public Operations addNewOperation(Operations operation) {
        return operationsDAO.addNewOperation(operation);
    }

    @Override
    public Operations deleteOperation(Operations operation) {
        return operationsDAO.deleteOperation(operation);
    }

    @Override
    public Operations changeOperationName(Operations operation) {
        return operationsDAO.changeOperationName(operation);
    }

    @Override
    public Operations changeOperationDate(Operations operation) {
        return operationsDAO.changeOperationDate(operation);
    }

    @Override
    public Operations changeOperationSalary(Operations operation) {
        return operationsDAO.changeOperationSalary(operation);
    }

    @Override
    public List<Operations> showAllOperations() {
        return operationsDAO.showAllOperations();
    }

}