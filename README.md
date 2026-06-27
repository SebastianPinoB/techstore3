Microservicios RESTful para gestion de catalogo. Java 21, Spring Boot, PostgreSQL, JWT.

<<<<<<< HEAD

Requisitos:
Java 21, Maven 3.9+, PostgreSQL 15 instalados.
base de datos `techstore` creada en PostgreSQL

Base de datos: `techstore`
Puerto: `5432`
usuario: `postgres`
contraseña: `admin123`


=======
>>>>>>> f8e09c5024505b3ba27a3ae5058468baa6ab7ddd
Proceso login en postman: post http://localhost:8080/auth/login 
{
    "username": "admin@techstore",
    "password": "admin123"
}

<<<<<<< HEAD
Proceso post de producto de ejemplo en formato Json
=======
Proceso post de producto raw JSOn
>>>>>>> f8e09c5024505b3ba27a3ae5058468baa6ab7ddd
{
    "nombre": "Teclado Razer",
    "descripcion": "Teclado retroiluminacion",
    "precio": 23000,
    "stock": 23,
    "categoria": "PC",
    "activo": true
}