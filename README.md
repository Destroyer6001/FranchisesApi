🏢 API REST – Gestión de Franquicias, Sucursales y Productos

API REST desarrollada con Spring Boot 3.0 y Java 17, diseñada para gestionar franquicias, sucursales y productos de forma sencilla y escalable.

🛠️ Tecnologías utilizadas

Java 17

Spring Boot 3.0

Spring Web

Spring Data JPA (Hibernate)

PostgreSQL

Maven

Lombok

Validaciones con Spring Boot

Swagger / OpenAPI

📋 Requisitos previos

Antes de iniciar, asegúrate de tener instalado:

Java JDK 17

Maven 3.8+

PostgreSQL 14+

Git

IDE recomendado: IntelliJ IDEA, Eclipse o VS Code

⚙️ Instalación y configuración
1. Clonar el proyecto
git clone https://github.com/TU_USUARIO/TU_REPO.git
cd TU_REPO

2. Configurar base de datos PostgreSQL
CREATE DATABASE tododatabase_kuw7;
CREATE USER myuser WITH ENCRYPTED PASSWORD 'mypassword';
GRANT ALL PRIVILEGES ON DATABASE tododatabase_kuw7 TO myuser;

3. Configurar application.properties
# Datos de la Base de Datos
spring.datasource.url=jdbc:postgresql://localhost:5432/tododatabase_kuw7
spring.datasource.username=myuser
spring.datasource.password=mypassword
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Puerto del servidor
server.port=8080

4. Dependencias Maven (pom.xml)

Incluye las siguientes dependencias principales:

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-ui</artifactId>
        <version>2.1.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

🚀 Ejecutar la aplicación

Limpiar y compilar proyecto:

mvn clean install


Ejecutar aplicación:

mvn spring-boot:run


API disponible en: http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui.html

📌 Endpoints principales
Recurso	Método	Descripción
/franchises	GET/POST/PUT/DELETE	CRUD de franquicias
/branches	GET/POST/PUT/DELETE	CRUD de sucursales
/products	GET/POST/PUT/DELETE	CRUD de productos
✅ Comprobación

Verifica que la API responda correctamente:

curl http://localhost:8080/franchises


Swagger UI mostrará todos los endpoints documentados e interactivos.

📝 Notas adicionales

Todos los cambios en las entidades se sincronizan automáticamente con la base de datos gracias a spring.jpa.hibernate.ddl-auto=update.

Asegúrate de que PostgreSQL esté corriendo antes de levantar la API.

Se puede cambiar el puerto del servidor en application.properties si 8080 ya está ocupado.
