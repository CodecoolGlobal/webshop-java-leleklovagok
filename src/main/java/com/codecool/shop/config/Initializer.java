package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebListener
public class Initializer implements ServletContextListener {

    private Object getElementById (String id, JSONArray jsonArray) {
        for (Object o : jsonArray) {
            JSONObject item = (JSONObject) o;
            String currentId = (String) item.get("id");

            if (id.equals(currentId)) {
                return item;
            }
        }
        return "{}";
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();


        // Read JSON files
        JSONParser parser = new JSONParser();
        JSONArray productCategoriesArray = null;
        JSONArray suppliersArray = null;
        JSONArray productsArray = null;
        String basePath = null;
        try {
            basePath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String folderPath = basePath + "/src/main/java/com/codecool/shop/config/";
        try {
            productCategoriesArray = (JSONArray) parser.parse(new FileReader(
                    folderPath + "productCategories.json"));
            suppliersArray = (JSONArray) parser.parse(new FileReader(
                    folderPath + "suppliers.json"));
            productsArray = (JSONArray) parser.parse(new FileReader(
                    folderPath + "products.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Create suppliers
        for (Object o : suppliersArray) {
            JSONObject item = (JSONObject) o;
            Supplier newRecord = new Supplier(
                    (String) item.get("name"),
                    (String) item.get("description"),
                    (String) item.get("img")
               );
            supplierDataStore.add(newRecord);
        }

        //Create product categories
        for (Object o : productCategoriesArray) {
            JSONObject item = (JSONObject) o;
            ProductCategory newRecord = new ProductCategory(
                    (String) item.get("name"),
                    (String) item.get("department"),
                    (String) item.get("description")
            );
            productCategoryDataStore.add(newRecord);
        }

        //Create products
        for (Object o : productsArray) {
            JSONObject item = (JSONObject) o;
            Long productCategoryNo = (Long) item.get("productCategory");
            Long supplierNo = (Long) item.get("supplier");
            Long defaultPrice = (Long) item.get("defaultPrice");
            Product newRecord = new Product(
                    (String) item.get("name"),
                    defaultPrice.intValue(),
                    (String) item.get("currencyString"),
                    (String) item.get("description"),
                    productCategoryDataStore.find(productCategoryNo.intValue()),
                    supplierDataStore.find(supplierNo.intValue()),
                    (String) item.get("img")
            );
            productDataStore.add(newRecord);
        }


    }
}
