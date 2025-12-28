README - API de Gestión de Franquicias, Sucursales y Productos
Descripción

Esta API está desarrollada con Spring Boot 3 y gestiona:

Franquicias

Sucursales (Branches)

Productos

Permite operaciones CRUD y mantiene relaciones entre entidades.

Requisitos Previos
1. Java JDK 17 o superior

Descarga:

Adoptium
 (OpenJDK)

Oracle

Configuración de variables de entorno:

Windows

setx JAVA_HOME "C:\Program Files\Java\jdk-17.0.x"
setx PATH "%JAVA_HOME%\bin;%PATH%"


Linux / Mac

export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export PATH=$JAVA_HOME/bin:$PATH


Verifica la instalación:

java -version

2. Maven 3.8 o superior

Descarga: Apache Maven

Configuración de variables de entorno:

Windows

setx M2_HOME "C:\apache-maven-3.9.5"
setx PATH "%M2_HOME%\bin;%PATH%"


Linux / Mac

export M2_HOME=/opt/maven
export PATH=$M2_HOME/bin:$PATH


Verifica la instalación:

mvn -version

3. PostgreSQL 14 o superior

Instala desde: PostgreSQL Downloads

Crear base de datos y usuario:

CREATE DATABASE tododatabase_kuw7;
CREATE USER myuser WITH ENCRYPTED PASSWORD 'mypassword';
GRANT ALL PRIVILEGES ON DATABASE tododatabase_kuw7 TO myuser;


Verifica que puedas conectarte:

psql -U myuser -d tododatabase_kuw7

Clonar el proyecto
git clone https://github.com/TU_USUARIO/TU_REPO.git
cd TU_REPO

Configuración del Proyecto
1. Archivo application.properties
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

2. Dependencias Maven (pom.xml)
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- PostgreSQL Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Validaciones -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- OpenAPI / Swagger -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-ui</artifactId>
        <version>2.1.0</version>
    </dependency>

    <!-- Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

Compilar y Ejecutar
1. Limpiar y construir proyecto
mvn clean install

2. Ejecutar aplicación
mvn spring-boot:run


La API estará disponible en: http://localhost:8080

Documentación Swagger: http://localhost:8080/swagger-ui.html

Endpoints Principales
Recurso	Método	Descripción
/franchises	GET/POST/PUT/DELETE	CRUD de franquicias
/branches	GET/POST/PUT/DELETE	CRUD de sucursales
/products	GET/POST/PUT/DELETE	CRUD de productos
Comprobación


Asegúrate de tener PostgreSQL corriendo antes de levantar la API.

Puedes cambiar el puerto del servidor en application.properties si 8080 ya está ocupado.
