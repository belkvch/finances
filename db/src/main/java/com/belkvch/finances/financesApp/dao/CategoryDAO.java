package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.entyti.Category;

import java.util.List;

public interface CategoryDAO {

    List<Category> showAllCategories();

    List<Category> showAllCategoriesForAdmin();

    List<Category> showCategoriesById(int id);

    Category addNewCategory(Category category);

    Category addCategoryAccountConn(Category category, int account_id);

    Category getLastCategory();

    Category getCategoryById(int category_id);

    Category changeCategoryName(Category category);

    Category deleteCategory(Category category);
}
