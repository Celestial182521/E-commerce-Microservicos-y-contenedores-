package com.ecommerce.api.service;

import com.ecommerce.api.model.UserCreate;
import com.ecommerce.api.model.Users;
import com.ecommerce.api.model.UserUpdate;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    public List<Users> getAllUsers() {
        List<Users> users = new ArrayList<>();

        Users u1 = new Users();
        u1.setId(1);
        u1.setName("Juan Diego Gomez");
        u1.setEmail("juan@gmail.com");
        u1.setAddress("Avenida Tecnologico #23 7600");

        Users u2 = new Users();
        u2.setId(2);
        u2.setName("Maria Lopez");
        u2.setEmail("maria@gmail.com");
        u2.setAddress("Calle 5 de Mayo #10");

        users.add(u1);
        users.add(u2);

        return users;
    }

    public Users createUser(UserCreate userCreate) {
        Users response = new Users();
        response.setId((int) (System.currentTimeMillis() % 100000));
        response.setName(userCreate.getName());
        response.setEmail(userCreate.getEmail());
        response.setAddress(userCreate.getAddress());

        return response;
    }

    public Users getUserById(Integer id) {
        Users response = new Users();
        response.setId(id);
        response.setName("Usuario de Ejemplo");
        response.setEmail("ejemplo@gmail.com");
        response.setAddress("Dirección de Ejemplo");

        return response;
    }

    public Users updateUser(Integer id, UserUpdate update) {
        Users response = new Users();
        response.setId(id);
        response.setName(update.getName() != null ? update.getName() : "Usuario de Ejemplo");
        response.setEmail(update.getEmail() != null ? update.getEmail() : "ejemplo@gmail.com");
        response.setAddress(update.getAddress() != null ? update.getAddress() : "Dirección de Ejemplo");

        return response;
    }

    public void deleteUser(Integer id) {
        // sin persistencia aún — lógica de eliminación pendiente de BD
    }
}
