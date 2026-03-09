# API del Sistema E-commerce

## Auth / Usuarios

### GET /users
Lista todos los usuarios.  
**Acceso:** admin

### GET /users/{id}
Obtiene un usuario específico por su ID.  
**Acceso:** admin

### POST /auth/register
Registra un nuevo usuario en el sistema.  
**Acceso:** público

### POST /auth/login
Inicia sesión y devuelve un token de autenticación.  
**Acceso:** público

### GET /auth/me
Obtiene el perfil del usuario autenticado.  
**Acceso:** usuario autenticado

### PATCH /users/{id}
Actualiza parcialmente la información de un usuario.  
**Acceso:** admin o dueño de la cuenta

### DELETE /users/{id}
Elimina un usuario.  
**Acceso:** admin o dueño de la cuenta

---

## Productos / Inventario

### GET /products
Lista todos los productos disponibles.  
**Acceso:** público

### GET /products/{id}
Obtiene un producto específico por su ID.  
**Acceso:** público

### POST /products
Crea un nuevo producto.  
**Acceso:** admin

### PATCH /products/{id}
Actualiza parcialmente un producto.  
**Acceso:** admin

### DELETE /products/{id}
Elimina un producto.  
**Acceso:** admin

---

## Pedidos

### GET /orders
Lista los pedidos del usuario autenticado.  
Si es admin, puede listar todos.  
**Acceso:** usuario autenticado / admin

### GET /orders/{id}
Obtiene un pedido específico incluyendo total, fecha e id del usuario.  
**Acceso:** usuario autenticado / admin

### POST /orders
Crea un nuevo pedido con sus items.  
**Acceso:** usuario autenticado

### PUT /orders/{id}
Reemplaza completamente un pedido.  
**Acceso:** admin

### PATCH /orders/{id}
Actualiza parcialmente un pedido, por ejemplo su estado.  
**Acceso:** admin o según regla de negocio

### DELETE /orders/{id}
Cancela o elimina un pedido, según la regla definida.  
**Acceso:** admin o dueño según política

---

## Detalles de Pedido

### GET /orders/{id}/items
Lista todos los detalles o items de un pedido.  
**Acceso:** usuario autenticado / admin

### GET /orders/{id}/items/{itemId}
Obtiene un item específico de un pedido.  
**Acceso:** usuario autenticado / admin

### POST /orders/{id}/items
Agrega un item al pedido.  
**Acceso:** usuario autenticado

### PATCH /orders/{id}/items/{itemId}
Modifica un item específico del pedido.  
**Acceso:** usuario autenticado / admin

### DELETE /orders/{id}/items/{itemId}
Elimina un item del pedido.  
**Acceso:** usuario autenticado / admin

---

## Pagos

### GET /orders/{id}/payment
Obtiene la información del pago asociado al pedido.  
**Acceso:** usuario autenticado / admin

### POST /orders/{id}/payment
Crea el pago correspondiente al pedido.  
**Acceso:** usuario autenticado

### PATCH /orders/{id}/payment
Actualiza el estado del pago, por ejemplo de PENDIENTE a PAGADO.  
**Acceso:** admin o sistema

### DELETE /orders/{id}/payment
Revierte o elimina el pago asociado al pedido.  
**Acceso:** admin