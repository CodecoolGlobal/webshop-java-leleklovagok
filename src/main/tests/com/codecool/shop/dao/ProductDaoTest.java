package com.codecool.shop.dao;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {
    private static Initializer initializer= new Initializer();
    private static Supplier supplier;
    private static ProductCategory productCategory;
    private static ProductCategoryDao productCategoryDao= ProductCategoryDaoMem.getInstance();
    private static SupplierDao supplierDao = SupplierDaoMem.getInstance();


    static Stream<ProductDao> productDao() {
        return Stream.of(ProductDaoMemJDBC.getInstance(), ProductDaoMem.getInstance());
    }

    @BeforeAll
    public static void init(){
        initializer.readFromJSON();
        supplier = supplierDao.find(2);
        productCategory = productCategoryDao.find(2);
    }

    @ParameterizedTest
    @MethodSource("productDao")
    void test_getAll_listSize(ProductDao productDao) {
        System.out.println("Check reading of all products");
        List<Product> testList =  productDao.getAll();
        assertEquals(7, testList.size());
    }

    @ParameterizedTest
    @MethodSource("productDao")
    void test_getBy_supplier(ProductDao productDao) {
        System.out.println("Check reading of products by supplier");
         List<Product> testList =  productDao.getBy(supplier);
        assertEquals(3, testList.size());
    }

    @ParameterizedTest
    @MethodSource("productDao")
    void test_getBy_productCategory(ProductDao productDao) {
        System.out.println("Check reading of products by product category");
        List<Product> testList =  productDao.getBy(productCategory);
        assertEquals(3, testList.size());
    }

    @ParameterizedTest
    @MethodSource("productDao")
    void test_getBy_supplier_productCategory(ProductDao productDao) {
        System.out.println("Check reading of products by product category and supplier");
        List<Product> testList =  productDao.getBy(productCategory, supplier);
        assertEquals(1, testList.size());
    }


    @ParameterizedTest
    @MethodSource("productDao")
    void test_find_nameCheck(ProductDao productDao) {
        System.out.println("Check product find by id");
        Product testRecord =  productDao.find(2);
        assertEquals("Aura polírozás", testRecord.name);
    }
}