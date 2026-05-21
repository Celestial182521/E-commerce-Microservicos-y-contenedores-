package com.ecommerce.api.mapper;

import com.ecommerce.api.entity.ProductoEntity;
import com.ecommerce.api.model.Products;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;

@ApplicationScoped
public class ProductMapper {

    public Products toModel(ProductoEntity e) {
        if (e == null) return null;
        Products p = new Products();
        p.setId(e.idProducto.intValue());
        p.setName(e.nombre);
        p.setDescription(e.descripcion);
        p.setPrice(e.precio != null ? e.precio.floatValue() : 0f);
        p.setStock(e.stock);
        return p;
    }

    public ProductoEntity toEntity(Products product) {
        if (product == null) return null;
        ProductoEntity entity = new ProductoEntity();
        entity.nombre      = product.getName();
        entity.descripcion = product.getDescription();
        entity.precio      = product.getPrice() != null
                ? new BigDecimal(product.getPrice().toString()) : BigDecimal.ZERO;
        entity.stock       = product.getStock() != null ? product.getStock() : 0;
        return entity;
    }
}
