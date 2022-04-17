package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.dao.DBManager.DBManager;
import com.belkvch.finances.financesApp.entyti.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultCategoryDAO implements CategoryDAO{
    private static volatile DefaultCategoryDAO instance;
    private static final String SELECT_ALL_CAT_FOR_ACCOUNT = "SELECT *" +
            "FROM category" +
            "    join account_category on category.id = account_category.category_id" +
            "    join accounts on accounts.id = account_category.account_id\n" +
            "WHERE accounts.id=?";

    private static final String SELECT_ALL = "SELECT *" +
            "FROM category" +
            "    join account_category on category.id = account_category.category_id" +
            "    join accounts on accounts.id = account_category.account_id";

    private static final String INSERT_CATEGORY = "insert into category(category_name)  VALUES(?)";
    private static final String INSERT_CATEGORY_ACCOUNT = "insert into account_category(account_id, category_id)  VALUES(?,?)";

    private static final String SELECT_MAX_CATEGORY = " SELECT * FROM category" +
            "    WHERE id = (SELECT MAX (id) FROM category);";

    private static final String UPDATE_CATEGORY_NAME = "update category set category_name = ? where id = ?";

    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM category WHERE id = ?";

    private static final String DELETE_CATEGORY = "delete from category where id = ?";


    private DefaultCategoryDAO() {
    }

    public static DefaultCategoryDAO getInstance() {
        if (instance == null) {
            synchronized (DefaultCategoryDAO.class) {
                if (instance == null) {
                    instance = new DefaultCategoryDAO();
                }
            }
        }
        return instance;
    }

    public Category initCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("category_name"));
        return category;
    }

    @Override
    public List<Category> showAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                categories.add(initCategory(resultSet));
            }
        } catch(
                SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<Category> showCategoriesById(int id) {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CAT_FOR_ACCOUNT);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                categories.add(initCategory(resultSet));
            }
        } catch(
                SQLException throwables)

        {
            throwables.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category addNewCategory(Category category) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY);
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    category.setId(resultSet.getInt("id"));
                }
            }
            return category;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Category addCategoryAccountConn(Category category, int account_id) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY_ACCOUNT);
            preparedStatement.setInt(1, account_id);
            preparedStatement.setInt(2, category.getId());
            preparedStatement.executeUpdate();
            return category;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Category getLastCategory() {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MAX_CATEGORY);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initCategory(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Category getCategoryById(int category_id) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID);
            preparedStatement.setInt(1, category_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return initCategory(resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Category changeCategoryName(Category category) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CATEGORY_NAME);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getId());
            preparedStatement.executeUpdate();
            return category;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Category deleteCategory(Category category) {
        try (Connection connection = DBManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CATEGORY);
            preparedStatement.setInt(1, category.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
