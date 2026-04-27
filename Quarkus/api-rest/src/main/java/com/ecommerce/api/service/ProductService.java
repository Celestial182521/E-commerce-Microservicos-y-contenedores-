package com.ecommerce.api.service;

import com.ecommerce.api.model.Products;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductService {

    public Products createProduct(Products product) {
        System.out.println("Service - Product recibido: " + product.getName());

        Products response = new Products();
        response.setId((int) (System.currentTimeMillis() % 100000));
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        return response;
    }

    public Products getProductById(Integer productId) {
        System.out.println("Service - Buscando Product con ID: " + productId);

        Products response = new Products();
        response.setId(productId);
        response.setName("Producto de Ejemplo");
        response.setDescription("Descripción de ejemplo");
        response.setPrice(99.99f);
        response.setStock(10);

        return response;
    }
}
