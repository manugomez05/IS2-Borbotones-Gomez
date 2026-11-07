# Ejercicio 2 - React con Spring Boot

Solución de referencia para la parte individual de React + API REST.

## Proyectos

- `Backend/`: API Spring Boot (Java 17, Maven) que consulta el servicio externo OpenWeatherMap.
- `Frontend/`: aplicación React con Vite.

## Ejecución

1. Copiá `Backend/src/main/resources/application-local.example.properties` como `application-local.properties` en la misma carpeta y reemplazá el marcador por tu clave. El archivo local está ignorado por Git; también podés configurar `OPENWEATHER_API_KEY`.
2. Desde `Backend`, ejecutá `mvnw.cmd spring-boot:run` (Windows) o `./mvnw spring-boot:run`.
3. Desde `Frontend`, ejecutá `npm install` y `npm run dev`.
4. El cliente apunta a `http://localhost:8080`; Vite corre en el puerto configurado en `vite.config.js`.

## Fuentes

- Video: https://www.youtube.com/watch?v=L5yihOAQOyI
- Backend: https://github.com/Dshan26/BackendAppClima (commit `a333863`)
- Frontend: https://github.com/Dshan26/FrontendAppClima (commit `789f319`)

La clave de API se configura localmente mediante el archivo de ejemplo; no subas tu clave al repositorio. También se admite la variable `OPENWEATHER_API_KEY`.