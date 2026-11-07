# Ejercicio 1 - React con Spring Boot

Solución de referencia para la parte individual de React + API REST.

## Proyectos

- `Backend/`: API Spring Boot (Java 17, Maven, MySQL).
- `Frontend/`: interfaz React.

## Ejecución

1. Iniciá MySQL en `127.0.0.1:3306`. Al arrancar, el backend crea `productdb` si todavía no existe. La configuración local de la base está en `Backend/src/main/resources/application-local.properties`; ese archivo está ignorado por Git.
2. Desde `Backend`, ejecutá `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`).
3. Copiá `Frontend/.env.example` como `Frontend/.env.local`, completá las credenciales de un usuario que hayas registrado, y desde `Frontend` ejecutá `npm install` y `npm start`.

La configuración local de React va en `.env.local`; el puerto del cliente es 3001 y la API usa 8081.

## Fuentes

- Video: https://www.youtube.com/watch?v=42XW4hwhjHA
- Backend: https://github.com/KindsonTheGenius/product-app-spring-api (commit `23c8366`)
- Frontend: https://github.com/KindsonTheGenius/product-app-react-ui (commit `60ce505`)