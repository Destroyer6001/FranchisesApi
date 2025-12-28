# 🏪 API Gestor de Franquicias - Spring Boot

API RESTful para gestión de franquicias, sucursales y productos desarrollada con Spring Boot 3.2+, PostgreSQL y Lombok.

## 🚀 Características

- Gestión completa de franquicias (CRUD)
- Administración de sucursales por franquicia
- Catálogo de productos asociados a sucursales
- Relaciones jerárquicas: Franquicia → Sucursal → Producto
- Validación de datos y manejo de errores estandarizado

## 🛠 Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot 3.2.x**
- **PostgreSQL 15+**
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias
- **Spring Data JPA** - Persistencia de datos
- **Spring Web** - API REST

## 📋 Prerrequisitos

### 1. Java Development Kit (JDK) 17 o superior
```bash
java -version
```

### 2. Maven 3.8+
```bash
mvn -v
```

### 3. Git
```bash
git --version
```

## ⚙️ Configuración del Entorno

### 1. Clonar el Repositorio
```bash
https://github.com/Destroyer6001/FranchisesApi.git
cd gestor-franquicias-api
```

### 2. Configurar la Aplicación

Editar `src/main/resources/application.properties` con las configuraciones necesarias.

### 3. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8088/api`

## 🔗 Enlace para Clonar

```bash
git clone https://github.com/tu-usuario/gestor-franquicias-api.git
```
