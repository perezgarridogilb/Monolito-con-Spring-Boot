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

## AWS ECR → AWS Lambda

Despliegue de la imagen como función Lambda usando el **Lambda Web Adapter**, que convierte
la app Spring Boot en un contenedor que responde a invocaciones de Lambda.

### 1. Configurar credenciales

El cliente de AWS se instala con `brew install aws-cli` y las credenciales se cargan desde
el archivo local `movie/.vars`:

```bash
cd movie
source .vars
aws sts get-caller-identity --query Account --output text   # verifica que la sesión es válida
```

### 2. Construir la imagen

Se fija `linux/amd64` porque Lambda ejecuta en x86_64 y la imagen base
`eclipse-temurin:17-jre-alpine` no publica manifiesto para arm64:

```bash
docker build --no-cache --provenance=false --sbom=false \
  --platform linux/amd64 -t movie:test .
```

### 3. Probar en local (opcional)

```bash
docker compose up -d --no-build movie
docker compose logs -f movie
docker compose down        # para detener
```

### 4. Autenticarse en ECR

```bash
aws ecr get-login-password --region $REGION \
  | docker login --username AWS --password-stdin \
      $ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com
```

### 5. Publicar la imagen

```bash
docker tag movie:test $ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com/$REPO_NAME:latest
docker push $ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com/$REPO_NAME:latest
```

### 6. Crear la función Lambda

Desde la consola de AWS, en **ECR → Repositorios**, abre el repositorio y elige
*AWS Lambda → Deploy*. Lambda detecta el Lambda Web Adapter en
`/opt/extensions/lambda-adapter` y conecta automáticamente con el repositorio.

Variables de entorno necesarias en la configuración de la función:

| Variable | Valor |
|---|---|
| `AWS_ACCESS_KEY_ID1` | |
| `AWS_SECRET_ACCESS_KEY1` | |
| `AWS_REGION1` | |
| `AWS_DEFAULT_REGION1` | |
| `DB_URL` | |
| `DB_USER` | |
| `DB_PASS` | |
| `SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE` | |

