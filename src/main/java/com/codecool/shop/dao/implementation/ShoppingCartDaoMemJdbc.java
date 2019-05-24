package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartDaoMemJdbc extends DatabaseDao implements ShoppingCartDao {
    private HashMap<Product, Integer> cart = new HashMap<>();
    private static ShoppingCartDaoMemJdbc instance = null;

    private ShoppingCartDaoMemJdbc() {
    }

    public static ShoppingCartDaoMemJdbc getInstance() {
        if (instance == null) {
            instance = new ShoppingCartDaoMemJdbc();
        }
        return instance;
    }


    @Override
    public void add(Product product) {
        if (cart.containsKey(product)) {
            cart.put(product, cart.get(product) + 1);
        } else {
            cart.put(product, 1);
        }
    }

    @Override
    public Product find(int id) {
        return cart.keySet().stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }


    @Override
    public void remove(Product product) {
        int quantity = cart.get(product);
        if (quantity > 1) {
            cart.put(product, quantity - 1);
        } else {
            cart.remove(product);
        }
    }


    @Override
    public HashMap<Product, Integer> getAll() {
        return cart;
    }


    @Override
    public int getTotalProductNr() {
        return cart.keySet().stream().mapToInt(key -> cart.get(key)).reduce(0, (x, y) -> x + y);
    }

    @Override
    public int getTotalPrice() {
        int totalPrice = 0;
        for (Map.Entry cartItem : cart.entrySet()) {
            totalPrice += ((Product) cartItem.getKey()).getDefaultPrice() * (int) cartItem.getValue();
        }
        return totalPrice;
    }

    @Override
    public Object getSize() {
        return cart.size();
    }

}
