package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.Operations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultOperationsDAO implements OperationsDAO {
    private static volatile DefaultOperationsDAO instance;
    private static final String SELECT_ALL = "select * from finances_bd";
    private static final String SELECT_OPERATION_BY_ID = "select * from finances_bd where id = ?";
    private static final String INSERT_OPERATION = "insert into finances_bd(name,date_op,salary)  VALUES(?,?,?)";
    private static final String UPDATE_OPERATION_NAME = "update finances_bd set name = ? where id = ?";
    private static final String UPDATE_OPERATION_DATE = "update finances_bd set date_op = ? where id = ?";
    private static final String UPDATE_OPERATION_SALARY = "update finances_bd set salary = ? where id = ?";
    private static final String DELETE_OPERATION = "delete from finances_bd where id = ?";
    private static final String SELECT_OPERATION_BY_NAME = "select * from finances_bd where name = ?";

    private DefaultOperationsDAO() {
    }

    public static DefaultOperationsDAO getInstance() {
        if (instance == null) {
            synchronized (DefaultOperationsDAO.class) {
                if (instance == null) {
                    instance = new DefaultOperationsDAO();
                }
            }
        }
        return instance;
    }

    public Operations initOperation(ResultSet resultSet) throws SQLException {
        Operations operation = new Operations();
        operation.setId(resultSet.getInt("id"));
        operation.setNameOfOperation(resultSet.getString("name"));
        operation.setDateOfOperation(resultSet.getDate("date_op"));
        operation.setPriceOfOperation(resultSet.getBigDecimal("salary"));
        return operation;
    }

    @Override
    public Operations getOperationById(int id) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OPERATION_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initOperation(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Operations getOperationByName(String name) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OPERATION_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initOperation(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Operations deleteOperation(Operations operation) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_OPERATION);
            preparedStatement.setInt(1, operation.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Operations> showAllOperations() {
        List<Operations> operations = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                operations.add(initOperation(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return operations;
    }

    @Override
    public Operations addNewOperation(Operations operation) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_OPERATION);
            preparedStatement.setString(1, operation.getNameOfOperation());
            java.util.Date utilDate = operation.getDateOfOperation();
            java.sql.Date date = new java.sql.Date(utilDate.getTime());
            preparedStatement.setDate(2, date);
            preparedStatement.setBigDecimal(3, operation.getPriceOfOperation());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    operation.setId(resultSet.getInt("id"));
                }
            }
            return operation;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

//    HELP ME, IDK, MAYBE WRONG SQL UPDATE, I TRIED WITH COMMAS, BUT IT DIDN'T WORK TOO :(((

    @Override
    public Operations changeOperationName(Operations operation) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OPERATION_NAME);
            preparedStatement.setString(1, operation.getNameOfOperation());
            preparedStatement.setInt(2, operation.getId());
            preparedStatement.executeUpdate();
            return operation;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Operations changeOperationDate(Operations operation) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OPERATION_DATE);
            java.util.Date utilDate = operation.getDateOfOperation();
            java.sql.Date date = new java.sql.Date(utilDate.getTime());
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, operation.getId());
            preparedStatement.executeUpdate();
            return operation;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Operations changeOperationSalary(Operations operation) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OPERATION_SALARY);
            preparedStatement.setBigDecimal(1, operation.getPriceOfOperation());
            preparedStatement.setInt(2, operation.getId());
            preparedStatement.executeUpdate();
            return operation;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
