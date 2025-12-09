# Sistema de Gestión de Biblioteca

Sistema de gestión de biblioteca desarrollado con Spring Boot para administrar libros, usuarios y préstamos.

## Requisitos Previos

- Java JDK 21 o superior
- Maven 3.6 o superior
- PostgreSQL 12 o superior
- Docker (opcional, para base de datos)
- Git

## Tecnologías Utilizadas

- Spring Boot 3.5.8
- Spring Data JPA
- Spring Security
- PostgreSQL
- Hibernate
- Lombok
- Swagger/OpenAPI
- Jakarta Validation

## Configuración de la Base de Datos



### Opción 1: PostgreSQL Local

1. Instala PostgreSQL en tu sistema
2. Crea una base de datos llamada `biblioteca`
3. Asegúrate de que el servidor esté corriendo en el puerto 5432

## Configuración de la Aplicación

1. Clona el repositorio:
```bash
git clone <url-del-repositorio>
cd ComponentesFinal
```

2. Configura las credenciales de la base de datos en `src/main/resources/application.properties`:

```properties
spring.application.name=ComponentesFinal

# Configuración de base de datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:8081/postgres
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Configuración de Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

Nota: Si usas PostgreSQL en el puerto por defecto (5432), cambia la URL a:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca
```

## Instalación y Ejecución

### Usando Maven

1. Compila el proyecto:
```bash
mvn clean install
```

2. Ejecuta la aplicación:
```bash
mvn spring-boot:run
```

### Usando el archivo JAR

1. Genera el archivo JAR:
```bash
mvn clean package
```

2. Ejecuta el JAR generado:
```bash
java -jar target/ComponentesFinal-0.0.1-SNAPSHOT.jar
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
  "titulo": "Effective Java",
  "autor": "Joshua Bloch",
  "isbn": "978-0134685991",
  "stock": 15,
  "fechaPublicacion": "2018-01-06T08:00:00.000+00:00"
}
```

### Crear un Usuario

```json
POST /v1/usuarios
{
  "username": "estudiante1",
  "password": "Pass123!",
  "email": "estudiante@biblioteca.com",
  "roles": ["ROL_ESTUDIANTE"]
}
```

### Crear un Préstamo

```json
POST /v1/prestamos
{
  "libroId": 1,
  "usuarioId": 1
}
```

## Roles Disponibles

- ROL_ADMIN - Administrador del sistema
- ROL_GESTOR_INVENTARIO - Gestión de inventario de libros
- ROL_BIBLIOTECARIO - Gestión de préstamos y devoluciones
- ROL_AUXILIAR - Soporte y consultas
- ROL_ESTUDIANTE - Usuario que puede realizar préstamos (REQUERIDO para préstamos)

## Reglas de Negocio

1. Solo usuarios con rol ESTUDIANTE pueden realizar préstamos
2. El ISBN de cada libro debe ser único
3. No se pueden realizar préstamos si no hay stock disponible
4. Los usuarios suspendidos no pueden realizar préstamos
5. Los préstamos tienen un plazo de 7 días por defecto
6. No se pueden devolver libros que ya han sido devueltos

## Solución de Problemas

### Error: "password authentication failed for user postgres"
- Verifica que las credenciales en application.properties coincidan con tu base de datos
- Si usas Docker, asegúrate de que el contenedor esté ejecutándose

### Error: "Maximum call stack size exceeded" en Swagger
- Este error ya está resuelto con las anotaciones @JsonManagedReference y @JsonBackReference

### Error: "Port 8080 is already in use"
- Cambia el puerto en application.properties:
```properties
server.port=8081
```

### Las tablas no se crean automáticamente
- Verifica que spring.jpa.hibernate.ddl-auto esté configurado como "create-drop" o "update"
- Asegúrate de que la conexión a la base de datos sea correcta

## Notas Importantes

- Con `spring.jpa.hibernate.ddl-auto=create-drop`, las tablas se eliminan al cerrar la aplicación
- Para producción, cambia a `update` o `validate`
- Los datos de ejemplo se pueden agregar usando la interfaz de Swagger
- Swagger UI permite probar todos los endpoints sin necesidad de herramientas adicionales

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

## Contribuciones

Para contribuir al proyecto:
1. Haz un fork del repositorio
2. Crea una rama para tu feature
3. Realiza tus cambios
4. Envía un pull request

## Licencia

Este proyecto es de uso académico.

