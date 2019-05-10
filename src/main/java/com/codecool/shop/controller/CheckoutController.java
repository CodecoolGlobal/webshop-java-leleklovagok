package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@WebServlet(urlPatterns = {"/checkout-cart"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        String reqSupplierId = request.getParameter("supplierId");
        String reqCategoryId = request.getParameter("categoryId");
        int supplierId = 0;
        int categoryId = 0;

        if (reqSupplierId != null) {
            supplierId = Integer.parseInt(reqSupplierId);
        }
        if (reqCategoryId != null) {
            categoryId = Integer.parseInt(reqCategoryId);
        }

        context.setVariable("currentCategory", categoryId);
        context.setVariable("currentSupplier", supplierId);




        engine.process("product/checkout.html", context, response.getWriter());

    }
}
