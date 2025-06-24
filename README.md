# Descripción del Proyecto

Este proyecto es una API REST desarrollada en Java con Spring Boot, diseñada para gestionar y almacenar estadísticas de contacto de clientes.
La aplicación implementa los principios de Clean Architecture, lo que garantiza una separación clara entre la lógica de negocio, la infraestructura y los puntos de entrada.

### Características principales:
Recibe estadísticas de contacto de clientes a través de endpoints REST.
Valida y almacena las estadísticas en una base de datos NoSQL (DynamoDB).
Publica eventos relacionados en un broker de mensajería (RabbitMQ) para su posterior procesamiento o integración con otros sistemas.
Facilita la integración con otros servicios y la escalabilidad gracias a su arquitectura desacoplada.


## Tecnologías Utilizadas
- **Java 21**
- **Spring Boot 3 (WebFlux)**
- **DynamoDB Local (NoSQL)**
- **RabbitMQ**
- **Gradle (multi-módulo)**
- **Docker & Docker Compose**
- **Lombok**
- **MapStruct**
- **JUnit 5 & Mockito**

## Requisitos Previos
Antes de comenzar, asegúrate de tener instalado lo siguiente en tu máquina:

- **Java 21**: Necesario para compilar y ejecutar la aplicación.
- **Docker y Docker Compose**: Para levantar la base de datos DynamoDB local, RabbitMQ y la propia aplicación en contenedores.
- **AWS CLI (opcional)**: Utilidad de línea de comandos de AWS, recomendada para crear la tabla en DynamoDB local.
- **Git (opcional)**: Para clonar el repositorio.
- **Gradle (opcional)**: El proyecto incluye el wrapper (./gradlew), por lo que no es obligatorio tener Gradle instalado globalmente.



## Guía de Ejecución
Sigue estos pasos para levantar la aplicación y sus dependencias en tu entorno local:

**1. Clonar el repositorio**
git clone <URL_DEL_REPOSITORIO>

**2. Construir el proyecto**
Compila el proyecto y genera el JAR de la aplicación:
./gradlew clean build

**3. Levantar los servicios con Docker Compose**
Esto iniciará DynamoDB local, RabbitMQ y la aplicación en contenedores:
docker-compose up --build -d

Puedes verificar que los servicios están corriendo con:
docker-compose ps

**4. Crear la tabla en DynamoDB**
Una vez que DynamoDB local esté corriendo, crea la tabla necesaria ejecutando el siguiente comando (requiere AWS CLI):
aws dynamodb create-table \
  --stats-table stats \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
  --endpoint-url http://localhost:8000 \
  --region us-east-1

**4.1. Consultar la tabla (opcional)**
Para verificar que la tabla fue creada correctamente y ver los datos almacenados:
aws dynamodb scan --table-name stats --endpoint-url http://localhost:8000 --no-cli-pager


**5. Probar la API**
La aplicación estará disponible en:
http://localhost:8082/api/v1/stats
Puedes probar los endpoints usando Postman, curl o cualquier cliente HTTP.


## Arquitectura
La aplicación implementa los principios de Clean Architecture, asegurando una separación clara entre la lógica de negocio, la infraestructura y los puntos de entrada.
A continuación se presentan los diagramas C4 que ilustran la arquitectura del sistema.


### Diagrama de Contexto C4
Este diagrama muestra cómo el sistema principal (API de Estadística) interactúa con los actores y sistemas externos.

![C4_Model-Estadística drawio](https://github.com/user-attachments/assets/5d1f8e58-a1eb-482c-bc95-f661a0cfab39)


### Diagrama de Contenedores C4
Este diagrama muestra los principales componentes internos de la aplicación y cómo interactúan entre sí.

![C4_Model-Estadística Container drawio](https://github.com/user-attachments/assets/923d0400-612e-4588-a2f7-93c2cce8689f)



## Estructura del Proyecto
El proyecto está organizado siguiendo los principios de Clean Architecture y una estructura multi-módulo con Gradle.
A continuación se describe la estructura principal de carpetas y módulos:


![image](https://github.com/user-attachments/assets/c8d7d229-ab38-44cb-bad3-b8bf54012bd6)


### Principales módulos:
- **applications/app-service**: Módulo principal, contiene la configuración de Spring Boot y el punto de entrada de la aplicación.
- **domain/model**: Entidades y lógica de negocio pura.
- **domain/usecase**: Casos de uso que orquestan la lógica de negocio.
- **infrastructure/driven-adapters**: Implementaciones concretas para interactuar con sistemas externos (DynamoDB, RabbitMQ).
- **infrastructure/entry-points/reactive-web**: API REST y configuración de endpoints.


## Ejecución de Tests y Reporte de Cobertura
Puedes ejecutar los tests unitarios y de integración del proyecto con el siguiente comando:
./gradlew test


### Para generar el reporte de cobertura de código global (todos los módulos) con JaCoCo, sigue estos pasos:

**1. Ubica la tarea JacocoMergedReport en el proyecto.**
Puedes encontrarla en: gradle/statistics-stats/task/other/JacocoMergedReport

O ejecuta directamente desde la raíz del proyecto:
./gradlew JacocoMergedReport

**2. El reporte HTML estará disponible en:**
build/reports/jacocoMergedReport/html/index.html


### Notas y Buenas Prácticas

**Perfiles de Spring Boot:**
El proyecto está configurado para ejecutarse en local por defecto (spring.profiles.include=local). No es necesario modificar los perfiles para pruebas técnicas o ejecución local.

**Configuración de DynamoDB y RabbitMQ:**
Los servicios de DynamoDB local y RabbitMQ se levantan automáticamente con Docker Compose. Asegúrate de que los puertos 8000 (DynamoDB) y 5672/15672 (RabbitMQ) estén libres en tu máquina.

**Creación de la tabla en DynamoDB:**
Recuerda crear la tabla stats en DynamoDB local antes de probar la API. Puedes usar el comando AWS CLI proporcionado en la guía de ejecución.

**Variables de entorno:**
Si necesitas personalizar la configuración (puertos, nombres de tabla, credenciales, etc.), puedes hacerlo editando el archivo application.yaml o agregando variables de entorno en el bloque correspondiente de docker-compose.yml.

**Ejecución de la aplicación:**
Siempre ejecuta primero los comandos de build (./gradlew clean build) antes de levantar los contenedores, para asegurarte de que el JAR esté actualizado.

**Pruebas y cobertura:**
Mantén una alta cobertura de tests. El reporte global de cobertura se genera con la tarea JacocoMergedReport y se encuentra en build/reports/jacocoMergedReport/html/index.html.

**Limpieza de contenedores e imágenes:**
Si necesitas limpiar el entorno, puedes detener y eliminar los contenedores con:
docker-compose down

**Y eliminar imágenes con:**
docker rmi <nombre_o_id_de_la_imagen>

### Buenas prácticas de código
- Sigue los principios de Clean Architecture.
- Documenta tus clases y métodos con JavaDoc.
- Usa commits atómicos y mensajes claros siguiendo Conventional Commits.
- Mantén el código limpio y modular.