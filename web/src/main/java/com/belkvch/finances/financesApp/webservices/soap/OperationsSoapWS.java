//package com.belkvch.finances.financesApp.webservices.soap;
//
//import com.belkvch.finances.financesApp.dao.DefaultOperationsDAO;
//import com.belkvch.finances.financesApp.entyti.Operations;
//
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//import javax.jws.WebService;
//import java.util.List;
//
//@WebService(name = "OperationsSoapWS", serviceName = "ws/soap/operations")
//public class OperationsSoapWS {
//
//    @WebMethod(operationName = "showAllOperations")
//    public List<Operations> showAllOperations() {
//        return DefaultOperationsDAO.getInstance().showAllOperations();
//    }
//
//    @WebMethod(operationName = "getOperationById")
//    public Operations getOperationById(@WebParam(name = "id") int id) {
//        return DefaultOperationsDAO.getInstance().getOperationById(id);
//    }
//
//    @WebMethod(operationName = "deleteOperation")
//    public Operations deleteOperation(@WebParam(name = "operation") Operations operation) {
//        return DefaultOperationsDAO.getInstance().deleteOperation(operation);
//    }
//
//}
