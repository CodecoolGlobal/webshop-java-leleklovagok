package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;

public interface ShoppingCartDao {
    void add(Product product);
    Product find(int id);
    void remove(int id);
    List<Product> getAll();
    int getSize();

}
