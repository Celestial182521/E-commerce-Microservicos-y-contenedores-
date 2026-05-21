package com.ecommerce.api.service;

import com.ecommerce.api.entity.ProductoEntity;
import com.ecommerce.api.mapper.ProductMapper;
import com.ecommerce.api.model.ProductUpdate;
import com.ecommerce.api.model.Products;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductMapper productMapper;

    public List<Products> getAllProducts() {
        return ProductoEntity.<ProductoEntity>listAll()
                .stream().map(productMapper::toModel).collect(Collectors.toList());
    }

    public Products getProductById(Integer id) {
        ProductoEntity entity = ProductoEntity.findById(id.longValue());
        if (entity == null)
            throw new WebApplicationException(
                Response.status(404).entity("Producto no encontrado").build());
        return productMapper.toModel(entity);
    }

    @Transactional
    public Products createProduct(Products product) {
        ProductoEntity entity = productMapper.toEntity(product);
        entity.persist();
        return productMapper.toModel(entity);
    }

    @Transactional
    public Products updateProduct(Integer id, ProductUpdate update) {
        ProductoEntity entity = ProductoEntity.findById(id.longValue());
        if (entity == null)
            throw new WebApplicationException(
                Response.status(404).entity("Producto no encontrado").build());
        if (update.getName() != null)  entity.nombre = update.getName();
        if (update.getPrice() != null) entity.precio = update.getPrice();
        if (update.getStock() != null) entity.stock  = update.getStock();
        return productMapper.toModel(entity);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        boolean deleted = ProductoEntity.deleteById(id.longValue());
        if (!deleted)
            throw new WebApplicationException(
                Response.status(404).entity("Producto no encontrado").build());
    }
}
