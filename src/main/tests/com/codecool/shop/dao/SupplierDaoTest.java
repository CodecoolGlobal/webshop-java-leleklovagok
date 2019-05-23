package com.codecool.shop.dao;

import com.codecool.shop.config.Initializer;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMemJdbc;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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


public class SupplierDaoTest {
    private static Initializer initializer= new Initializer();
    static Stream<SupplierDao> supplierDao() {
        return Stream.of(SupplierDaoMemJdbc.getInstance(), SupplierDaoMem.getInstance());
    }

    @BeforeAll
    public static void init(){
        initializer.readFromJSON();
    }

    @ParameterizedTest
    @MethodSource("supplierDao")
    void test_getAll_listSize(SupplierDao supplierDao) {
        System.out.println("Check reading of all suppliers");
        List<Supplier> testList =  supplierDao.getAll();
        assertEquals(2, testList.size());
    }

    @ParameterizedTest
    @MethodSource("supplierDao")
    void test_find_nameCheck(SupplierDao supplierDao) {
        System.out.println("Check supplier find by id");
        Supplier testRecord =  supplierDao.find(2);
        assertEquals("Lala a gyógyító", testRecord.name);
    }




}
