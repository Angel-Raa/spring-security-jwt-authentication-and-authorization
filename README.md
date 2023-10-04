# spring security jwt authentication and authorization

Si encuentras útil este repositorio, ¡por favor ayúdanos marcándolo con una ⭐! 😊

## ¿Qué son los JSON Web Tokens (JWT)?

Los JSON Web Tokens (JWT) son un estándar abierto para la transmisión segura de información en formato JSON. Están compuestos por tres partes separadas por puntos:

1. **Header**: Contiene información sobre el token, como el algoritmo de firma utilizado.

2. **Payload**: Incluye los datos del token, como el nombre de usuario, la fecha de expiración, entre otros.

3. **Signature**: Es una firma digital utilizada para verificar la autenticidad del token.

Los JWT se utilizan ampliamente para la autenticación y autorización de usuarios en aplicaciones web. Ofrecen la ventaja de permitir el envío seguro de información entre servidores y clientes, sin necesidad de almacenar datos sensibles en el servidor.

## Ventajas de los JWT

- **Seguridad**: Los JWT se pueden firmar digitalmente para verificar su autenticidad, lo que los hace resistentes a la falsificación.

- **Eficiencia**: Los JWT son ligeros y fáciles de transportar debido a su formato compacto.

- **Facilidad de uso**: Implementar JWT en aplicaciones web es sencillo gracias a su estructura y a las bibliotecas disponibles.

## Usos comunes de los JWT

1. **Autenticación**: Los JWT se utilizan para autenticar a los usuarios en aplicaciones web. El servidor emite un JWT después de que el usuario se haya autenticado con éxito, y este token se utiliza para verificar la identidad del usuario en las solicitudes posteriores.

2. **Autorización**: Los JWT se pueden utilizar para autorizar a los usuarios a acceder a recursos específicos. Los claims (reclamos) en el payload del JWT pueden contener información sobre los roles o permisos del usuario.

3. **Intercambio de información**: Los JWT se emplean para intercambiar información de forma segura entre aplicaciones web. Esto es útil en casos como la comunicación entre microservicios o la autenticación en una API.


## Tecnologías Utilizadas

- JDK 17
- Spring Boot 3
- Spring Data Jpa
- Spring security 6
- PostgreSQL 15
- Java JWT
- Docker

## Prerrequisitos

Asegúrate de tener instalados los siguientes componentes en tu entorno de desarrollo antes de comenzar:

1. [Git](https://git-scm.com/downloads)
2. [Docker](https://docs.docker.com/compose/install/)

## Configuración del Entorno 

#### Clonar el repositorio en tu máquina local

```
git clone https://github.com/Angel-Raa/spring-security-jwt-authentication-and-authorization.git
```
#### Navegar al directorio del proyecto
Dirígete al directorio del proyecto recién clonado utilizando el siguiente comando:
```
cd spring-security-jwt-authentication-and-authorization
```
#### Crear y Configurar file.env y db.env

Debes crear dos archivos de configuración, `file.env` y `db.env`, en el directorio raíz del repositorio clonado. 

**file.env** 
Este archivo contiene las variables de entorno para la aplicación.
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/tu_basedatos
SPRING_DATASOURCE_USERNAME=tu_usuario
SPRING_DATASOURCE_PASSWORD=tu_contraseña
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW=true
```
**db.env** 
Este archivo contiene las credenciales para la base de datos.
```bash
POSTGRES_DB=nombre_de_tu_basedatos
POSTGRES_PASSWORD=contraseña_de_la_base_de_datos
POSTGRES_USER=usuario_de_la_base_de_datos
```
Asegúrate de reemplazar `nombre_de_tu_basedatos`, `contraseña_de_la_base_de_datos` y `usuario_de_la_base_de_datos` con los valores adecuados para tu configuración.

#### Construir y ejecutar los contenedores de Docker Compose
```
docker compose up -d
```

### Documentación de los Endpoints
```
http://localhost:8000/api/v1/swagger-ui/index.html#/
```
![](https://github.com/Angel-Raa/spring-security-jwt-authentication-and-authorization/blob/main/src/main/resources/img/docs.png)




Esperamos que esta documentación te ayude a comprender mejor cómo usar los JSON Web Tokens (JWT) en tu aplicación de Spring Security. Si tienes alguna pregunta o sugerencia, no dudes en abrir un problema o contribuir al repositorio.





