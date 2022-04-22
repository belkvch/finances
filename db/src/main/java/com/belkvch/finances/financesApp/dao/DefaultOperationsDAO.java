package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.Category;
import com.belkvch.finances.financesApp.entyti.Operations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultOperationsDAO implements OperationsDAO {
    private static volatile DefaultOperationsDAO instance;
    private static final String SELECT_ALL = "select * from operations, category where operations.category_id = category.id";
    private static final String SELECT_ALL_FOR_ACCOUNT = "select * from operations, category where operations.account_id=? and operations.date_op=? and operations.category_id = category.id";
    private static final String SELECT_OPERATION_BY_ID = "select * from operations, category where operations.id = ? and operations.category_id = category.id";
    private static final String SELECT_OPERATION_FOR_HISTORY = "select * from operations, category where operations.account_id=? and operations.category_id = category.id";
    private static final String INSERT_OPERATION = "insert into operations(name,date_op,salary,account_id,category_id)  VALUES(?,?,?,?,?)";
    private static final String UPDATE_OPERATION_NAME = "update operations set name = ? where id = ?";
    private static final String UPDATE_OPERATION_DATE = "update operations set date_op = ? where id = ?";
    private static final String UPDATE_OPERATION_SALARY = "update operations set salary = ? where id = ?";
    private static final String DELETE_OPERATION = "delete from operations where id = ?";
    private static final String SELECT_OPERATION_BY_NAME = "select * from operations where name = ?";
    private static final String UPDATE_OPERATION_CATEGORY = "update operations set category_id = ? where id = ?";
    private static final String UPDATE_OPERATION_CATEGORY_FOR_DELETE = "update operations set category_id = 1 where category_id = ?";

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
        operation.setAccountId(resultSet.getInt("account_id"));
        operation.setCategoryId(new Category(resultSet.getInt("id"), resultSet.getString("category_name")));
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
    public Operations changeOperationCategory(Operations operation) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OPERATION_CATEGORY);
            preparedStatement.setObject(1, operation.getCategoryId().getId());
            preparedStatement.setInt(2, operation.getId());
            preparedStatement.executeUpdate();
            return operation;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void changeOperationCategoryToNull(int category_id) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OPERATION_CATEGORY_FOR_DELETE);
            preparedStatement.setInt(1, category_id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public List<Operations> showAllOperationsForHistory(int id) {
        List<Operations> operations = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OPERATION_FOR_HISTORY);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                operations.add(initOperation(resultSet));
            }
        } catch(
                SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return operations;
    }

    @Override
    public List<Operations> showAllOperationsForAccount(int id, Date date) {
        List<Operations> operations = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FOR_ACCOUNT);
            preparedStatement.setInt(1, id);
            preparedStatement.setDate(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
            operations.add(initOperation(resultSet));
        }
    } catch(
    SQLException throwables)
    {
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
            preparedStatement.setInt(4, operation.getAccountId());
            preparedStatement.setObject(5, operation.getCategoryId().getId());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    operation.setId(resultSet.getInt("id"));
                }
            }
            return operation;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return null;
    }

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
