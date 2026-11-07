# Ejercicio 5 - React con Spring Boot y seguridad

Solución de referencia para la parte individual de React + Java con autenticación y seguridad.

## Proyectos

- `Backend/`: API Spring Boot con Spring Security, JPA y MySQL (Java 17).
- `Frontend/`: interfaz React.

## Ejecución

1. Iniciá MySQL en `127.0.0.1:3306`. Al arrancar, el backend crea `productdb` si todavía no existe. La configuración local de la base está en `Backend/src/main/resources/application-local.properties`; ese archivo está ignorado por Git.
2. Desde `Backend`, ejecutá `mvnw.cmd spring-boot:run` (Windows) o `./mvnw spring-boot:run`.
3. Desde `Frontend`, ejecutá `npm install` y `npm start`.
4. Copiá `Frontend/.env.example` como `Frontend/.env.local`, completá las credenciales de un usuario registrado y usá el cliente en el puerto 3001. La API usa el puerto 8081.

## Fuentes

- Video: https://www.youtube.com/watch?v=W4hBA2gACik&list=PL9l1zUfnZkZnmca4gYPVQQ9msSR5tQTdk&index=6
- Backend: https://github.com/KindsonTheGenius/product-app-spring-api (commit `23c8366`)
- Frontend: https://github.com/KindsonTheGenius/product-app-react-ui (commit `60ce505`)