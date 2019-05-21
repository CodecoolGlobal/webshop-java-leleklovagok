package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.*; // Database addition
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoMemJdbc implements ProductCategoryDao {
    // Database addition:
    private static final String DATABASE = "jdbc:postgresql://173.212.197.253:54321/leleklovagok";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "b1735e9e68e371193d5aa0e0b906da60";


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
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }




    // Database general: -----------------------------------------------------
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    private void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
