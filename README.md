# 🏪 API Gestor de Franquicias - Spring Boot

API RESTful para gestión de franquicias, sucursales y productos desarrollada con Spring Boot 3.5.9+, PostgreSQL y Lombok.

## 🚀 Características

- Gestión completa de franquicias (CRUD)
- Administración de sucursales por franquicia
- Catálogo de productos asociados a sucursales
- Relaciones jerárquicas: Franquicia → Sucursal → Producto
- Validación de datos y manejo de errores estandarizado

## 🛠 Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot 3.5.9x**
- **PostgreSQL 14+**
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
git clone https://github.com/Destroyer6001/FranchisesApi.git
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
git clone  https://github.com/Destroyer6001/FranchisesApi.git
```

# 🌐 Endpoints API Gestor de Franquicias

## 📊 Endpoints Disponibles

### **Franquicias**
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **GET** | `https://franchisesapi.onrender.com/api/franchises` | Obtiene todas las franquicias |
| **GET** | `https://franchisesapi.onrender.com/api/franchises/{id}` | Obtiene franquicia por ID |
| **POST** | `https://franchisesapi.onrender.com/api/franchises` | Crea una nueva franquicia |
| **PUT** | `https://franchisesapi.onrender.com/api/franchises/{id}` | Actualiza franquicia existente |
| **DELETE** | `https://franchisesapi.onrender.com/api/franchises/{id}` | Elimina una franquicia |
| **GET** | `https://franchisesapi.onrender.com/api/franchises/getBranchMaxProducts/{id}` | Obtiene sucursales con mayor stock por franquicia |

### **Sucursales**
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **GET** | `https://franchisesapi.onrender.com/api/branches/getAll/{idFranchise}` | Obtiene sucursales por franquicia |
| **GET** | `https://franchisesapi.onrender.com/api/branches/{id}` | Obtiene sucursal por ID |
| **POST** | `https://franchisesapi.onrender.com/api/branches` | Crea una nueva sucursal |
| **PUT** | `https://franchisesapi.onrender.com/api/branches/{id}` | Actualiza sucursal existente |
| **DELETE** | `https://franchisesapi.onrender.com/api/branches/{id}` | Elimina una sucursal |

### **Productos**
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **GET** | `https://franchisesapi.onrender.com/api/products/getAll/{idBranch}` | Obtiene productos por sucursal |
| **GET** | `https://franchisesapi.onrender.com/api/products/{id}` | Obtiene producto por ID |
| **POST** | `https://franchisesapi.onrender.com/api/products` | Crea un nuevo producto |
| **PUT** | `https://franchisesapi.onrender.com/api/products/{id}` | Actualiza producto existente |
| **DELETE** | `https://franchisesapi.onrender.com/api/products/{id}` | Elimina un producto |

## 🔑 Parámetros

### Path Parameters
- `{id}`: ID del recurso (franquicia, sucursal o producto)
- `{idFranchise}`: ID de la franquicia
- `{idBranch}`: ID de la sucursal

## 📄 Formato de Respuesta

Todas las respuestas siguen el formato `ApiResponseDTO<T>`:
```json
{
  "success": true,
  "message": "Operación exitosa",
  "data": { ... },
  "timestamp": "2024-01-15T10:30:00Z"
}
```
## 🔗 URL Base
```
https://franchisesapi.onrender.com
```
