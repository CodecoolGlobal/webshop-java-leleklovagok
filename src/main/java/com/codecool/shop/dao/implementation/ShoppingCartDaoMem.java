package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;

import java.util.HashMap;

public class ShoppingCartDaoMem implements ShoppingCartDao {
    private HashMap<Product, Integer> cart = new HashMap<>();
    private static ShoppingCartDaoMem instance = null;

    private ShoppingCartDaoMem() {
    }

    public static ShoppingCartDaoMem getInstance() {
        if (instance == null) {
            instance = new ShoppingCartDaoMem();
        }
        return instance;
    }


    @Override
    public void add(Product product) {
        if (cart.containsKey(product)) {
            cart.get(product)++;
        }
        cart.put(product, 1);
    }

    @Override
    public Product find(int id) {
        return cart.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }


    @Override
    public void remove(Product product) {
        cart.remove(product);
    }

    @Override
    public HashMap<Product, Integer> getAll() {
        return cart;
    }

    @Override
    public int getSize() {
        return cart.size();
    }

}
