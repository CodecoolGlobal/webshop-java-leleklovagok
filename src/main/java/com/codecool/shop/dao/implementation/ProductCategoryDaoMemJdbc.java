package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*; // Database addition
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoMemJdbc extends DatabaseDao implements ProductCategoryDao {

    private List<ProductCategory> data = new ArrayList<>();
    private static ProductCategoryDaoMemJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    public ProductCategoryDaoMemJdbc() {
    }


    public static ProductCategoryDaoMemJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoMemJdbc();
        }
        return instance;
    }
    // Updated methods to db-------------------------
    @Override
    public void add(ProductCategory productCategory) {
        String query = "INSERT INTO suppliers (name, description, department) " +
                "VALUES ('" + productCategory.name + "', '" + productCategory.description+ "', '" + productCategory.department + "');";
        executeQuery(query);
    }
    @Override
    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM product_categories;";

        List<ProductCategory> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                ProductCategory record = new ProductCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description")
                );
                resultList.add(record);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;

    }

    // Not updated methods--------------------------------

    @Override
    public ProductCategory find(int id) {
        String query = "SELECT * FROM product_categories WHERE id=" + id + ";";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                ProductCategory record = new ProductCategory(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description")
                );
                return record;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void removeAll() {
        data.clear();
    }

}
