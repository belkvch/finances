package com.belkvch.finances.service;

import com.belkvch.finances.entyti.Operations;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface OperationsService {
    Operations getOperationById(int id) throws ParseException;

    Operations addNewOperation() throws IOException, ParseException;

    Operations deleteOperationById(int id);

    Operations changeOperationById(Integer id) throws ParseException;

    List<Operations> showAllOperations();
}