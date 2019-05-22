package com.codecool.shop.dao.implementation;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*; // Database addition

public abstract class DatabaseDao {
    // Database addition:
    protected static final String DATABASE = "jdbc:postgresql://173.212.197.253:54321/leleklovagok";
    protected static final String DB_USER = "postgres";
    protected static final String DB_PASSWORD = "b1735e9e68e371193d5aa0e0b906da60";


    // Database general: -----------------------------------------------------
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    protected void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
