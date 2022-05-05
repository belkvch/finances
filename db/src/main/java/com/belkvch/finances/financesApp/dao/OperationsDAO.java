package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.entyti.Operations;

import java.sql.Date;
import java.util.List;

public interface OperationsDAO {
    Operations getOperationById(int id);
    List<Operations> showAllOperations();
    List<Operations> showAllOperationsForHistory(int id);
    List<Operations> showAllOperationsForAccount(int id, Date date);

    Operations addNewOperation(Operations operations);

    void changeOperationCategoryToNull(int category_id);

    Operations deleteOperation(Operations operations);

//    Operations addNewOperation(Operations operation, int id);

    Operations changeOperationName(Operations operations);
    Operations changeOperationDate(Operations operation);
    Operations changeOperationSalary(Operations operation);
    Operations getOperationByName(String name);

    Operations changeOperationCategory(Operations operation);
}
