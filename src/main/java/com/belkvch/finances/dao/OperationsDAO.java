package com.belkvch.finances.dao;

import com.belkvch.finances.entyti.Operations;

import java.util.List;

public interface OperationsDAO {
    Operations getOperationById(int id);
    List<Operations> showAllOperations();
    Operations addNewOperation(Operations operations);
    Operations deleteOperation(Operations cat);
    Operations changeOperation(Operations cat);
    Operations getOperationByName(String name);
}
