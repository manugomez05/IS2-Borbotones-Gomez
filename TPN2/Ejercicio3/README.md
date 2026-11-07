# Ejercicio 3 - React con Java

Solución de referencia para la parte individual de React + API REST.

## Proyectos

- `Backend/`: API Spring Boot (Java 17, Maven).
- `Frontend/`: aplicación React.

## Ejecución

1. Ejecutá una vez `Backend/SQL/Script.sql` en HeidiSQL para crear la base y las tablas. Copiá `Backend/src/main/resources/application-local.example.properties` a `application-local.properties` en la misma carpeta y completá la contraseña local de MySQL; el archivo local está ignorado por Git.
2. Desde `Backend`, ejecutá `mvnw.cmd spring-boot:run` (Windows) o `./mvnw spring-boot:run`.
3. Desde `Frontend`, ejecutá `npm install` y `npm start`.

## Fuentes

- Video: https://www.youtube.com/watch?v=LuM5c_f6nUE
- Backend: https://github.com/YheremiRamos/BACK_REACT_PRUEBA_TECNICA (commit `d97fd73`)
- Frontend: https://github.com/YheremiRamos/FRONT_REACT_PRUEBA_TECNICA (commit `c120136`)