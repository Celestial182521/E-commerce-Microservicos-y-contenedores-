# Arquitectura del Sistema

## Descripción general

El sistema de e-commerce está diseñado bajo una arquitectura de microservicios, donde cada módulo del negocio funciona de manera independiente y se comunica mediante API REST. Este enfoque permite dividir responsabilidades, facilitar el mantenimiento, mejorar la escalabilidad y hacer más clara la organización del proyecto.

La plataforma cubre las funciones principales de un comercio electrónico: autenticación de usuarios, gestión de productos, control de pedidos, detalle de artículos comprados y procesamiento de pagos.

---

## Objetivo de la arquitectura

La arquitectura busca:

- separar responsabilidades por dominio
- facilitar el desarrollo colaborativo
- permitir que cada servicio evolucione de forma independiente
- mejorar la mantenibilidad del sistema
- hacer posible el despliegue en contenedores

---

## Microservicios principales



### 1. User Service
Responsable de la administración de usuarios.

Funciones principales:

- registrar usuarios
- iniciar sesión
- listar usuarios
- btener usuario por ID
- actualizar datos de usuario
- eliminar usuario
- control de acceso según rol o propietario

Endpoints relacionados:

 - GET /users
 - GET /users/{id}
 - POST /users
 - POST /users/login
 - PATCH /users/{id}
 - DELETE /users/{id}

---

### 2. Product Service
Responsable de la gestión de productos e inventario.

Funciones principales:

- listar productos
- consultar producto específico
- registrar nuevos productos
- actualizar información del producto
- eliminar productos
- controlar disponibilidad e inventario

Endpoints relacionados:

- `GET /products`
- `GET /products/{id}`
- `POST /products`
- `PATCH /products/{id}`
- `DELETE /products/{id}`

---

### 3. Order Service
Responsable de la gestión de pedidos.

Funciones principales:

- crear pedidos
- consultar pedidos del usuario
- consultar pedido específico
- actualizar pedido
- cancelar o eliminar pedido
- almacenar total, fecha y usuario asociado

Endpoints relacionados:

- `GET /orders`
- `GET /orders/{id}`
- `POST /orders`
- `PUT /orders/{id}`
- `PATCH /orders/{id}`
- `DELETE /orders/{id}`

---

### 4. Order Items Service
Responsable de la gestión del detalle de cada pedido.

Funciones principales:

- listar productos incluidos en un pedido
- obtener detalle de un item específico
- agregar productos al pedido
- modificar cantidades o datos del item
- eliminar items del pedido

Endpoints relacionados:

- `GET /orders/{id}/items`
- `GET /orders/{id}/items/{itemId}`
- `POST /orders/{id}/items`
- `PATCH /orders/{id}/items/{itemId}`
- `DELETE /orders/{id}/items/{itemId}`

---

### . Payment Service
Responsable del procesamiento y control de pagos.

Funciones principales:

- crear pago asociado a un pedido
- consultar el pago de un pedido
- actualizar estado del pago
- revertir o eliminar pago según reglas del negocio

Endpoints relacionados:

- `GET /orders/{id}/payment`
- `POST /orders/{id}/payment`
- `PATCH /orders/{id}/payment`
- `DELETE /orders/{id}/payment`

---

## Relación entre servicios

La comunicación entre servicios sigue una lógica de negocio estructurada:

1. Un usuario se registra o inicia sesión en el sistema mediante el Auth Service.
2. Una vez autenticado, puede consultar productos disponibles en el Product Service.
3. El usuario crea un pedido en el Order Service.
4. Los productos concretos del pedido se administran en Order Items Service.
5. Finalmente, el pago del pedido se procesa en Payment Service.
6. Los administradores pueden consultar usuarios, productos, pedidos y pagos según sus permisos.

---

## Roles del sistema

El sistema considera dos roles principales:

### Admin
Puede:

- listar usuarios
- consultar cualquier usuario
- crear, editar y eliminar productos
- consultar todos los pedidos
- actualizar estados de pedidos y pagos
- eliminar recursos según reglas definidas

### User
Puede:

- registrarse e iniciar sesión
- consultar su perfil
- consultar productos
- crear pedidos
- ver sus propios pedidos
- administrar sus propios items de pedido
- generar pagos de sus pedidos
- modificar o eliminar su cuenta según políticas del sistema

---

## Estados del sistema

### Estados de pedido
- `PENDIENTE`
- `CONFIRMADO`
- `ENVIADO`
- `ENTREGADO`
- `CANCELADO`

### Estados de pago
- `PENDIENTE`
- `PAGADO`
- `FALLIDO`
- `REEMBOLSADO`

---

## Base de datos

Cada microservicio puede tener su propia responsabilidad sobre la información que maneja. De manera general, el sistema contempla las siguientes entidades:

- usuarios
- productos
- pedidos
- detalles de pedido
- pagos

Esto permite separar la lógica del negocio y mantener una estructura clara para el almacenamiento de datos.

---

## Despliegue

El sistema está pensado para ejecutarse en contenedores Docker, permitiendo:

- despliegue uniforme en cualquier entorno
- facilidad para desarrollo colaborativo
- aislamiento de servicios
- escalabilidad modular

Cada microservicio podrá ejecutarse de forma independiente y posteriormente integrarse mediante `docker-compose`.

---

## Ventajas de esta arquitectura

- modularidad
- mantenimiento más sencillo
- escalabilidad por servicio
- trabajo colaborativo por equipos
- mejor organización del código
- facilidad para documentar APIs y procesos

---

## Conclusión

La arquitectura propuesta para el sistema de e-commerce permite dividir el proyecto en componentes claros, independientes y escalables. Gracias al uso de microservicios, cada módulo atiende una responsabilidad específica del negocio, lo que facilita tanto el desarrollo como la documentación, integración y despliegue del sistema.