package com.belkvch.finances.financesApp.service;

import com.belkvch.finances.financesApp.entyti.Operations;

import javax.ejb.Remote;
import java.text.ParseException;
import java.util.List;

@Remote
public interface OperationsService {
    Operations getOperationById(int id) throws ParseException;

    Operations addNewOperation(Operations operation);

    Operations deleteOperation(Operations operation);

    Operations changeOperationName(Operations operation) throws ParseException;
    Operations changeOperationDate(Operations operation);
    Operations changeOperationSalary(Operations operation);

    List<Operations> showAllOperations();

    Operations getOperationByName(String name);
}