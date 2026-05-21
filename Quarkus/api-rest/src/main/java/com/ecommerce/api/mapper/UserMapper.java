package com.ecommerce.api.mapper;

import com.ecommerce.api.entity.UsuarioEntity;
import com.ecommerce.api.model.UserCreate;
import com.ecommerce.api.model.Users;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {

    public Users toModel(UsuarioEntity e) {
        if (e == null) return null;
        Users u = new Users();
        u.setId(e.idUser.intValue());
        u.setName(e.nombre);
        u.setEmail(e.correo);
        u.setAddress(e.direccion);
        return u;
    }

    public UsuarioEntity toEntity(UserCreate create) {
        if (create == null) return null;
        UsuarioEntity entity = new UsuarioEntity();
        entity.nombre     = create.getName();
        entity.correo     = create.getEmail();
        entity.contrasena = create.getPassword();
        entity.direccion  = create.getAddress();
        return entity;
    }
}
