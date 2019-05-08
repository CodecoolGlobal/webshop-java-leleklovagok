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
import java.nio.file.Paths;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

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



        /*setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

         */

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
                    (String) item.get("description")
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
                    //productCategoryDataStore.find(1 ),
                    //supplierDataStore.find(1)
            );
            productDataStore.add(newRecord);
        }


        // ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");

        // productCategoryDataStore.add(tablet);

        //setting up products and printing it
        // productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        // productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        // productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
    }
}
