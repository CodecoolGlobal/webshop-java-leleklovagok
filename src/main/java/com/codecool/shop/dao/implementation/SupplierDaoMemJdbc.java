package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;


import java.sql.*; // Database addition
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoMemJdbc implements SupplierDao {
    // Database addition:
    private static final String DATABASE = "jdbc:postgresql://173.212.197.253:54321/leleklovagok";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "b1735e9e68e371193d5aa0e0b906da60";

    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoMemJdbc instance = null;

    // Updated methods to db-------------------------
    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO suppliers (name, description, img) " +
                "VALUES ('" + supplier.name + "', '" + supplier.description+ "', '" + supplier.img + "');";
        executeQuery(query);
    }

    @Override
    public List<Supplier> getAll() {
        String query = "SELECT * FROM suppliers;";

        List<Supplier> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                Supplier record = new Supplier(
                        resultSet.getString("name"),
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

    // Not updated methods--------------------------------
    @Override
    public Supplier find(int id) {
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
