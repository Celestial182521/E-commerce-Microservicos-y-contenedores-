package com.ecommerce.api.service;

import com.ecommerce.api.entity.UsuarioEntity;
import com.ecommerce.api.mapper.UserMapper;
import com.ecommerce.api.model.UserCreate;
import com.ecommerce.api.model.UserUpdate;
import com.ecommerce.api.model.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserMapper userMapper;

    public List<Users> getAllUsers() {
        return UsuarioEntity.<UsuarioEntity>listAll()
                .stream().map(userMapper::toModel).collect(Collectors.toList());
    }

    public Users getUserById(Integer id) {
        UsuarioEntity entity = UsuarioEntity.findById(id.longValue());
        if (entity == null)
            throw new WebApplicationException(
                Response.status(404).entity("Usuario no encontrado").build());
        return userMapper.toModel(entity);
    }

    @Transactional
    public Users createUser(UserCreate userCreate) {
        UsuarioEntity entity = userMapper.toEntity(userCreate);
        entity.persist();
        return userMapper.toModel(entity);
    }

    @Transactional
    public Users updateUser(Integer id, UserUpdate update) {
        UsuarioEntity entity = UsuarioEntity.findById(id.longValue());
        if (entity == null)
            throw new WebApplicationException(
                Response.status(404).entity("Usuario no encontrado").build());
        if (update.getName() != null)    entity.nombre    = update.getName();
        if (update.getEmail() != null)   entity.correo    = update.getEmail();
        if (update.getAddress() != null) entity.direccion = update.getAddress();
        return userMapper.toModel(entity);
    }

    @Transactional
    public void deleteUser(Integer id) {
        boolean deleted = UsuarioEntity.deleteById(id.longValue());
        if (!deleted)
            throw new WebApplicationException(
                Response.status(404).entity("Usuario no encontrado").build());
    }
}
