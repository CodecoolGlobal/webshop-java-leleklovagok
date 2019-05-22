package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*; // Database addition
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMemJDBC extends DatabaseDao implements ProductDao {
    private List<Product> data = new ArrayList<>();

    private static ProductDaoMemJDBC instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMemJDBC() {
    }

    public static ProductDaoMemJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoMemJDBC();
        }
        return instance;
    }

    private List<Product> getQuery(String query) {
        List<Product> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                Product record = new Product(
                        resultSet.getString("name"),
                        resultSet.getFloat("defaultPrice"),
                        resultSet.getString("currencyString"),
                        resultSet.getString("description"),
                        resultSet.getString("img")
                );
                resultList.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public void add(Product product) {
        //TODO - no need for it.
    }

    @Override
    public Product find(int id) {
        //TODO
        return null;
    }

    @Override
    public void remove(int id) {
        //TODO
    }


    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products;";
        return getQuery(query);
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        String query = "SELECT * FROM products WHERE supplier=" + supplier.getId() + ";";
        return getQuery(query);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        String query = "SELECT * FROM products WHERE product_category=" + productCategory.getId() + ";";
        return getQuery(query);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
        String query = "SELECT * FROM products WHERE product_category=" + productCategory.getId() + "AND supplier=" + supplier.getId() + ";";
        return getQuery(query);
    }
}
