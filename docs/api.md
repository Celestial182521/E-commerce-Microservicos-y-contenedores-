# API del Sistema

La API del sistema permite interactuar con los diferentes módulos del e-commerce mediante endpoints REST.

---

 Usuarios

GET /users
Lista todos los usuarios registrados.
(Solo accesible para administradores)

GET /users/{id}
Obtiene la información de un usuario específico.

POST /users
Crea un nuevo usuario en el sistema.

PATCH /users/{id}
Actualiza parcialmente la información de un usuario.

DELETE /users/{id}
Elimina un usuario del sistema.
---

# Productos

**GET /products**
Lista productos disponibles.

**GET /products/{id}**
Obtiene un producto específico.

**POST /products**
Crea producto (admin).

**PATCH /products/{id}**
Actualiza producto (admin).

**DELETE /products/{id}**
Elimina producto (admin).

---

# Carrito

**GET /cart**
Obtiene el carrito del usuario autenticado.

**POST /cart/items**
Agrega producto al carrito.

**PATCH /cart/items/{itemId}**
Modifica cantidad del producto.

**DELETE /cart/items/{itemId}**
Elimina producto del carrito.

**DELETE /cart**
Vacía completamente el carrito.

---

# Pedidos

**GET /orders**
Lista pedidos del usuario (o todos si es admin).

**GET /orders/{id}**
Obtiene un pedido específico.

**POST /orders**
Crea un pedido a partir del carrito.

**PATCH /orders/{id}**
Actualiza estado del pedido.

**DELETE /orders/{id}**
Cancela pedido.

---

# Detalles de Pedido

**GET /orders/{id}/items**
Lista items del pedido.

**GET /orders/{id}/items/{itemId}**
Obtiene un item específico.

---

# Pagos

**GET /orders/{id}/payment**
Obtiene el pago asociado.

**POST /orders/{id}/payment**
Realiza el pago del pedido.

**PATCH /orders/{id}/payment**
Actualiza estado del pago.

**DELETE /orders/{id}/payment**
Revierte o elimina el pago.
