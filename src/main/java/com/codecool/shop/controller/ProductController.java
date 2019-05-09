package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ShoppingCartDao shoppingCartStore = ShoppingCartDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDaoMem supplierDataStore = SupplierDaoMem.getInstance();
        resp.setCharacterEncoding("UTF-8");
//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        //NullPointerException HERE, id is null at first launch
        String reqSupplierId = req.getParameter("supplierId");
        String reqCategoryId = req.getParameter("categoryId");
        int supplierId = 0;
        int categoryId = 0;

        if (reqSupplierId != null) {
            supplierId = Integer.parseInt(reqSupplierId);
        }
        if (reqCategoryId != null) {
            categoryId = Integer.parseInt(reqCategoryId);
        }


        String addId = req.getParameter("id");

        if (addId != null) {
            shoppingCartStore.add(productDataStore.find(Integer.parseInt(addId)));
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("suppliers", supplierDataStore.getAll());
        context.setVariable("categories", productCategoryDataStore.getAll());

        if (supplierId > 0 && categoryId > 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId), supplierDataStore.find(supplierId)));
            context.setVariable("supplier", supplierDataStore.find(supplierId));

        } else if (supplierId > 0) {
            context.setVariable("products", productDataStore.getBy(supplierDataStore.find(supplierId)));
            context.setVariable("supplier", supplierDataStore.find(supplierId));
        } else if (categoryId > 0) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
        } else {
            context.setVariable("products", productDataStore.getAll());
        }

        context.setVariable("currentCategory", categoryId);
        context.setVariable("currentSupplier", supplierId);

        context.setVariable("quantity", shoppingCartStore.getTotalProductNr());
        engine.process("product/index.html", context, resp.getWriter());

    }

}
