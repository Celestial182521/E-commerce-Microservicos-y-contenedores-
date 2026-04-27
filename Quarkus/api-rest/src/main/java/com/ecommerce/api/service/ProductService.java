package com.ecommerce.api.service;

import com.ecommerce.api.model.Products;
import com.ecommerce.api.model.ProductUpdate;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductService {

    public List<Products> getAllProducts() {
        List<Products> products = new ArrayList<>();

        Products p1 = new Products();
        p1.setId(1);
        p1.setName("Mochila");
        p1.setDescription("Mochila Negra");
        p1.setPrice(1250.50f);
        p1.setStock(25);

        Products p2 = new Products();
        p2.setId(2);
        p2.setName("Laptop");
        p2.setDescription("Laptop 15 pulgadas");
        p2.setPrice(15999.99f);
        p2.setStock(10);

        products.add(p1);
        products.add(p2);

        return products;
    }

    public Products createProduct(Products product) {
        Products response = new Products();
        response.setId((int) (System.currentTimeMillis() % 100000));
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        return response;
    }

    public Products updateProduct(Integer id, ProductUpdate update) {
        Products response = new Products();
        response.setId(id);
        response.setName(update.getName() != null ? update.getName() : "Producto de Ejemplo");
        response.setPrice(update.getPrice() != null ? update.getPrice().floatValue() : 99.99f);
        response.setStock(update.getStock() != null ? update.getStock() : 10);

        return response;
    }

    public void deleteProduct(Integer id) {
        // sin persistencia aún — lógica de eliminación pendiente de BD
    }
}
