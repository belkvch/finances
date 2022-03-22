package com.belkvch.finances.service;

import com.belkvch.finances.entyti.Operations;
import java.text.ParseException;
import java.util.List;

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