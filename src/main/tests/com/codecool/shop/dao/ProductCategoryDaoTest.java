package com.codecool.shop.dao;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMemJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoMemJdbc;
import com.codecool.shop.model.ProductCategory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class ProductCategoryDaoTest {
    private static Initializer initializer= new Initializer();

    static Stream<ProductCategoryDao> productCategoryDao() {
        return Stream.of(ProductCategoryDaoMemJdbc.getInstance(), ProductCategoryDaoMem.getInstance());
    }

    @BeforeAll
    public static void init(){
        initializer.readFromJSON();
    }

    @ParameterizedTest
    @MethodSource("productCategoryDao")
    void test_getAll_listSize(ProductCategoryDao productCategoryDao) {
        System.out.println("Check reading of all product categories");
        List<ProductCategory> testList =  productCategoryDao.getAll();
        assertEquals(3, testList.size());
    }

    @ParameterizedTest
    @MethodSource("productCategoryDao")
    void test_find_nameCheck(ProductCategoryDao productCategoryDao) {
        System.out.println("Check product category find by id");
        ProductCategory testRecord =  productCategoryDao.find(3);
        assertEquals("Codecool termékek", testRecord.name);
    }

}