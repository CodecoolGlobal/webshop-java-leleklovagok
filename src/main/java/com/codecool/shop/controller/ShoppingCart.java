package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/shopping-cart"})
public class ShoppingCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        ShoppingCartDao shoppingCartStore = ShoppingCartDaoMem.getInstance();


        String addId = req.getParameter("add_id");
        String removeId = req.getParameter("remove_id");

        if (addId != null) {
            shoppingCartStore.add(shoppingCartStore.find(Integer.parseInt(addId)));
        }

        if (removeId != null) {
            shoppingCartStore.remove(shoppingCartStore.find(Integer.parseInt(removeId)));
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("quantity", shoppingCartStore.getTotalProductNr());
        context.setVariable("products", shoppingCartStore.getAll());
        context.setVariable("totalPrice", shoppingCartStore.getTotalPrice());
        engine.process("product/cart.html", context, resp.getWriter());

    }
}
