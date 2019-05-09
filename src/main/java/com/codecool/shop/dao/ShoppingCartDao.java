package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.HashMap;

public interface ShoppingCartDao {
    void add(Product product);
    Product find(int id);
    void remove(Product product);
    HashMap<Product, Integer> getAll();
    int getTotalProductNr();
    int getTotalPrice();

}
