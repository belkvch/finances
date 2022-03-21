package com.belkvch.finances.service;

import com.belkvch.finances.entyti.Operations;
import java.text.ParseException;
import java.util.List;

public interface OperationsService {
    Operations getOperationById(int id) throws ParseException;

    Operations addNewOperation(Operations operation);

    Operations deleteOperation(Operations operation);

    Operations changeOperation(Operations operation) throws ParseException;

    List<Operations> showAllOperations();

    Operations getOperationByName(String name);
}