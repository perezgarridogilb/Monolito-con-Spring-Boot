# 1. Imagen base con Java. Se fija linux/amd64 porque Lambda corre en x86_64
#    y este tag de temurin no publica manifiesto arm64.
FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine

# 2. Copiar el ejecutable de AWS Lambda Web Adapter desde la imagen de ECR
COPY --from=public.ecr.aws/awsguru/aws-lambda-adapter:0.8.4 /lambda-adapter /opt/extensions/lambda-adapter

# 3. Directorio de trabajo
WORKDIR /app

# 4. Copiar el ejecutable JAR generado por Maven
COPY target/movie-0.0.1-SNAPSHOT.jar app.jar

# 5. Configuración de puertos y verificaciones para el Web Adapter
ENV PORT=8001
ENV AWS_LWA_READINESS_CHECK_PORT=8001
ENV AWS_LWA_READINESS_CHECK_PATH=/

# 6. Arranque: ÚNICAMENTE ejecutar la aplicación Java
# (Lambda ejecuta la extensión automáticamente)
CMD ["java", "-jar", "app.jar"]
