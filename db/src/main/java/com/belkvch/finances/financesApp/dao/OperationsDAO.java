package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.entyti.Operations;

import java.util.List;

public interface OperationsDAO {
    Operations getOperationById(int id);
    List<Operations> showAllOperations();

    List<Operations> showAllOperationsForAccount(int id);

    Operations addNewOperation(Operations operations);
    Operations deleteOperation(Operations operations);
    Operations changeOperationName(Operations operations);
    Operations changeOperationDate(Operations operation);
    Operations changeOperationSalary(Operations operation);
    Operations getOperationByName(String name);
}
