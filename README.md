# Sistema de Gestión de Biblioteca

Sistema de gestión de biblioteca desarrollado con Spring Boot para administrar libros, usuarios y préstamos.

## Requisitos Previos

- Java JDK 21 o superior
- Maven 3.6 o superior
- PostgreSQL 12 o superior
- Git

## Configuración de la Base de Datos

### Opción 1: PostgreSQL Local

1. Instala PostgreSQL en tu sistema
2. Crea una base de datos llamada `componentesfinaldb`
3. Asegúrate de que el servidor esté corriendo en el puerto 5432

## Configuración de la Aplicación

1. Clona el repositorio:

```bash
  git clone https://github.com/MartinTrillo22/ComponentesFinal.git
  cd ComponentesFinal
```

2. Configura las credenciales de la base de datos en `src/main/resources/application.properties`:

```properties
spring.application.name=ComponentesFinal

# Configuración de base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/componentesfinaldb
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

```

## Instalación y Ejecución

### Usando Maven

1. Compila el proyecto:

```bash
   .\mvn clean install
```

2. Ejecuta la aplicación:

```bash
   .\mvn spring-boot:run
```

### Usando el IDE

1. Abre el proyecto en tu IDE (IntelliJ IDEA, Eclipse, etc.)
2. Ejecuta la clase principal: `ComponentesFinalApplication.java`

## Verificación

Una vez que la aplicación esté ejecutándose, deberías ver en la consola:

```
Started ComponentesFinalApplication in X.XXX seconds
```

La aplicación estará disponible en:

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## Endpoints Principales

### Libros

- GET /v1/libros - Obtener todos los libros
- GET /v1/libros/{id} - Obtener un libro por ID
- POST /v1/libros - Crear un nuevo libro
- PUT /v1/libros/{id} - Actualizar un libro
- DELETE /v1/libros/{id} - Eliminar un libro

### Usuarios

- GET /v1/usuarios - Obtener todos los usuarios
- GET /v1/usuarios/{id} - Obtener un usuario por ID
- POST /v1/usuarios - Crear un nuevo usuario
- PUT /v1/usuarios/{id} - Actualizar un usuario
- DELETE /v1/usuarios/{id} - Eliminar un usuario

### Préstamos

- GET /v1/prestamos - Obtener todos los préstamos
- GET /v1/prestamos/{id} - Obtener un préstamo por ID
- POST /v1/prestamos - Crear un nuevo préstamo
- PUT /v1/prestamos/{id}/devolver - Devolver un libro

## Ejemplos de Uso

### Crear un Libro

```json
POST /v1/libros
{
  "titulo": "Database System Concepts",
  "autor": "Abraham Silberschatz",
  "isbn": "978-0078022159",
  "stock": 12,
  "fechaPublicacion": "2019-04-18T00:00:00.000+00:00"
}
```

### Crear un Usuario

```json
POST /v1/usuarios
{
  "nombre": "Pedro Garcia",
  "email": "estudiante3@biblioteca.com",
  "password": "estudiante123",
  "telefono": "932396874",
  "direccion": "av primavera 123",
  "rolesIds": [
    4
  ]
}
```

### Crear un Préstamo

```json
POST /v1/prestamos
{
  "libroId": 1,
  "usuarioId": 5
}
```

## Roles Disponibles

- ROL_ADMIN - Administrador del sistema
- ROL_GESTOR_INVENTARIO - Gestión de inventario de libros
- ROL_BIBLIOTECARIO - Gestión de préstamos y devoluciones
- ROL_AUXILIAR - Soporte y consultas
- ROL_ESTUDIANTE - Usuario que puede realizar préstamos (REQUERIDO para préstamos)

## Reglas de Negocio

1. Solo se pueden realizar préstamos a usuarios con rol ESTUDIANTE
2. El ISBN de cada libro debe ser único
3. No se pueden realizar préstamos si no hay stock disponible
4. Los usuarios suspendidos no pueden realizar préstamos
5. Los préstamos tienen un plazo de 7 días por defecto
6. No se pueden devolver libros que ya han sido devueltos

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── auth/              # Configuración de seguridad
│   │   ├── config/            # Configuraciones (Swagger)
│   │   ├── exception/         # Excepciones personalizadas
│   │   ├── libro/             # Módulo de libros
│   │   ├── prestamos/         # Módulo de préstamos
│   │   ├── shared/            # Clases compartidas
│   │   └── usuarios/          # Módulo de usuarios
│   └── resources/
│       └── application.properties
└── test/
```

## Licencia

Este proyecto es de uso académico.
