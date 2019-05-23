package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;

import java.util.List;

public interface SupplierDao {

    public void add(Supplier supplier);
    public Supplier find(int id);
    public void remove(int id);
    public List<Supplier> getAll();
    void removeAll();
    // public void update(String id, String title);
}
