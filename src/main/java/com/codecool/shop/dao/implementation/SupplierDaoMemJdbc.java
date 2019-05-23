package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;


import java.sql.*; // Database addition
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoMemJdbc extends DatabaseDao implements SupplierDao {

    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoMemJdbc instance = null;

    public SupplierDaoMemJdbc() {
    }

    public static SupplierDaoMemJdbc getInstance() {
        if (instance == null) {
            instance = new SupplierDaoMemJdbc();
        }
        return instance;
    }

    // Updated methods to db-------------------------
    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO suppliers (name, description, img) " +
                "VALUES ('" + supplier.name + "', '" + supplier.description + "', '" + supplier.img + "');";
        executeQuery(query);
    }

    @Override
    public List<Supplier> getAll() {
        String query = "SELECT * FROM suppliers;";

        List<Supplier> resultList = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                Supplier record = new Supplier(
                        resultSet.getInt("id"),
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
        String query = "SELECT * FROM suppliers WHERE id=" + id + ";";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                Supplier record = new Supplier(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("img")
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
