# 🎬 Sistema de Gestión de Películas (Monolito con Spring Boot)

Aplicación monolítica Java para gestionar un catálogo de películas, construida con Spring Boot 4, Thymeleaf y MySQL.

## ✨ Características

- CRUD de películas, géneros y usuarios
- Clasificación por género y proveedor
- Registro de usuarios con validación (`@Valid`, email único)
- Panel de administración para gestión de roles
- Subida de archivos (imágenes de películas)

## 🔐 Seguridad dual

| Tipo | Autenticación | Protocolo |
|------|--------------|-----------|
| Web | Formulario Thymeleaf + sesiones | Spring Security + BCrypt |
| API REST (`/api/**`) | Stateless JWT | Login en `/api/login`, token `Bearer` |

## 🛠 Stack tecnológico

Java 21 · Spring Boot 4.1 · Spring Security 7 · JPA/Hibernate · Thymeleaf · MySQL · JJWT · Lombok · Jackson

<img width="2880" height="1468" alt="monolith" src="https://github.com/user-attachments/assets/d96a8d91-85f0-4284-bc06-50d3d09a3e88" />
