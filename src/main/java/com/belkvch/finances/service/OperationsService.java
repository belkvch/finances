package com.belkvch.finances.service;

import com.belkvch.finances.entyti.Operations;
import java.io.IOException;
import java.text.ParseException;

public interface OperationsService {
    Operations getOperationById(int id);

    Operations addNewOperation() throws IOException, ParseException;

    Operations deleteOperationById(Integer id);

    Operations changeOperationById (Integer id) throws ParseException;
}