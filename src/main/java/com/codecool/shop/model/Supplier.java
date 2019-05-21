package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier extends BaseModel {
    private List<Product> products;
    public String img;

    public Supplier(String name, String description, String img) {
        super(name, description);
        this.products = new ArrayList<>();
        this.setImg(img);
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s" +
                        "img: %4$s",
                this.id,
                this.name,
                this.description,
                this.img
        );
    }
}