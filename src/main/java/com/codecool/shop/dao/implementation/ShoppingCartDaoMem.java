package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDaoMem implements ShoppingCartDao {
    private List<Product> cart = new ArrayList<>();
    private int quantity;
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
        cart.add(product);
    }

    @Override
    public Product find(int id) {
        return cart.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }


    @Override
    public void remove(int id) {
        cart.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        return cart;
    }

    @Override
    public int getSize() {
        return cart.size();
    }

}
