# Arquitectura de TaskFlow

Esta guía rápida ayuda a un desarrollador que llega nuevo a TaskFlow: capas y paquetes, recorrido de `POST /projects/{projectId}/tasks`, dónde están las reglas de negocio, cómo funciona la seguridad JWT y cómo están organizados los tests.

## Capas y paquetes

- Controlador (frontera HTTP): paquete `com.taskflow.controller`.
  - Ej.: `src/main/java/com/taskflow/controller/TaskController.java`, `src/main/java/com/taskflow/controller/ProjectController.java`, `src/main/java/com/taskflow/controller/AuthController.java`.
- Servicio (casos de uso / orquestación): paquete `com.taskflow.service`.
  - Ej.: `src/main/java/com/taskflow/service/TaskService.java`.
- Repositorio (persistencia): paquete `com.taskflow.repository`.
  - Ej.: `src/main/java/com/taskflow/repository/TaskRepository.java`, `src/main/java/com/taskflow/repository/ProjectRepository.java`.
- Modelo / Dominio (entidades con reglas): paquete `com.taskflow.model`.
  - Ej.: `src/main/java/com/taskflow/model/Task.java`.
- Mappers (DTO ↔ Entidad): paquete `com.taskflow.mapper`.
  - Ej.: `src/main/java/com/taskflow/mapper/TaskMapper.java`.
- Seguridad: paquete `com.taskflow.security` y configuración relacionada.
  - Ej.: `src/main/java/com/taskflow/security/JwtService.java`, `src/main/java/com/taskflow/security/JwtAuthenticationFilter.java`, `src/main/java/com/taskflow/security/ProjectSecurity.java`.
- Advice / manejo de errores global: `src/main/java/com/taskflow/advice/GlobalExceptionHandler.java`.
- Config y seeder: `src/main/java/com/taskflow/config/DataSeeder.java`, `src/main/java/com/taskflow/config/OpenApiConfig.java`.

## Recorrido de `POST /projects/{projectId}/tasks`

1. Cliente hace POST a `/projects/{projectId}/tasks` con un JSON conforme al DTO (p.ej. `TaskRequest`).
2. Spring deserializa y aplica Bean Validation en el DTO; si falla, `GlobalExceptionHandler` responde 400.
3. El controlador `TaskController.createTask` (`src/main/java/com/taskflow/controller/TaskController.java`) recibe el `projectId` del path y comprueba existencia llamando a `ProjectService.buscarPorId` (`src/main/java/com/taskflow/service/ProjectService.java`). Si no existe -> `ProjectNotFoundException` -> 404.
4. Si el proyecto existe, el controlador llama a `TaskService.crear(request, projectId)` (`src/main/java/com/taskflow/service/TaskService.java`).
5. `TaskService.crear(...)` convierte el DTO y el `projectId` a entidad usando `TaskMapper.aEntidadNueva(request, projectId)` (`src/main/java/com/taskflow/mapper/TaskMapper.java`) y guarda la entidad con `TaskRepository.save(...)` (`src/main/java/com/taskflow/repository/TaskRepository.java`), que asigna el id.
6. El controlador responde `201 Created` con cabecera `Location` apuntando a `/tasks/{id}` y cuerpo `TaskResponse` obtenido con `TaskMapper.aResponse(creada)` (`src/main/java/com/taskflow/mapper/TaskMapper.java`).
7. Violaciones de reglas de dominio en la factory `Task.crear(...)` suben como `TaskValidationException` (traducidas a 400 por `GlobalExceptionHandler`); violaciones de transición de estado se traducen a `TaskStateException` -> 422.

## Dónde viven las reglas de negocio

- Reglas invariantes y transiciones de estado: dentro de la entidad `Task` (`src/main/java/com/taskflow/model/Task.java`). Ejemplos:
  - Factory `Task.crear(...)` (status inicial, dueDate no pasado al crear).
  - `setStatus(...)` (validación de transiciones como "no DONE sin assignee").
- Reglas de orquestación / casos de uso (Checks que requieren acceso a repositorios o múltiples entidades) viven en los servicios: `TaskService` y otros en `src/main/java/com/taskflow/service`.
- Validaciones de frontera (formato, tamaño, nulls) están en los DTOs con Bean Validation y se ejecutan antes de llegar al service.
- Traducción uniforme de excepciones a respuestas HTTP centralizada en `src/main/java/com/taskflow/advice/GlobalExceptionHandler.java`.

Regla práctica: reutilizar las validaciones del dominio en lugar de copiarlas en los services o controllers; los servicios orquestan, pero la lógica que define "qué es una tarea válida" está en `Task`.

## Seguridad con JWT

- Servicio de tokens: `src/main/java/com/taskflow/security/JwtService.java` emite y valida JWTs.
- Filtro de autenticación: `src/main/java/com/taskflow/security/JwtAuthenticationFilter.java` intercepta peticiones, extrae el token `Authorization: Bearer <token>`, valida con `JwtService` y, si es válido, carga la identidad en el `SecurityContext`.
- Rutas públicas: `/auth/**`, `/info`, Swagger y consola H2 (configuradas en seguridad). Todo lo demás requiere token.
- Autorización fina: métodos con anotaciones `@PreAuthorize` usan `com.taskflow.security.ProjectSecurity` y otros componentes para verificar permisos (por ejemplo, borrar un proyecto solo permite al owner o a un `ADMIN`). Ej.: `src/main/java/com/taskflow/security/ProjectSecurity.java`.
- Login y registro: `src/main/java/com/taskflow/controller/AuthController.java` expone `/auth/login` (público); en login se usan `AuthenticationManager` y `JwtService` para devolver el token.

Notas operativas:
- El filtro JWT corre antes del DispatcherServlet; por eso algunas excepciones de autenticación no pasan por `GlobalExceptionHandler` y se gestionan en la configuración de seguridad.
- Los tokens son stateless: no hay sesión almacenada en servidor.

## Organización de tests

- Tests unitarios: bajo `src/test/java/com/taskflow/*` usan JUnit 5 y Mockito. Son pruebas rápidas que no arrancan Spring.
- Tests slice: `@WebMvcTest`, `@DataJpaTest` para probar controladores o repositorios aisladamente con solo la porción necesaria del contexto.
- Tests de integración: `@SpringBootTest` con perfil `test` (y opcionalmente Testcontainers para integración real con BD). Los tests de integración pesados están en `*IT.java` y solo se ejecutan si `-Ddocker.tests=true` (no correr por defecto esta semana).
  - Ejemplos: `src/test/java/com/taskflow/integration/SecurityRulesTest.java`.
- Datos de ejemplo: al arrancar con perfil `h2` se usa `src/main/java/com/taskflow/config/DataSeeder.java` para poblar usuarios (`ana`, `luis`, `admin`) y datos de ejemplo.

## Buenas prácticas al contribuir

- No mover reglas del dominio hacia controllers o services: mantener `Task` como fuente de verdad.
- Endpoints que crean: devolver `201 Created` + `Location`.
- Usar DTOs (`record`) validados con `@Valid` en el controller; nunca exponer entidades directamente.
- Revisar `GlobalExceptionHandler` antes de añadir handlers nuevos para no romper la uniformidad de respuestas.

---

Archivo(s) principales referenciados (ejemplos):
- `src/main/java/com/taskflow/controller/TaskController.java`
- `src/main/java/com/taskflow/service/TaskService.java`
- `src/main/java/com/taskflow/mapper/TaskMapper.java`
- `src/main/java/com/taskflow/model/Task.java`
- `src/main/java/com/taskflow/repository/TaskRepository.java`
- `src/main/java/com/taskflow/advice/GlobalExceptionHandler.java`
- `src/main/java/com/taskflow/security/JwtService.java`
- `src/main/java/com/taskflow/security/JwtAuthenticationFilter.java`
- `src/main/java/com/taskflow/security/ProjectSecurity.java`
- `src/main/java/com/taskflow/config/DataSeeder.java`

Si quieres, añado diagramas de flujo o ejemplos concretos de payloads (request/response) para `POST /projects/{projectId}/tasks` y ejemplos de excepciones y códigos HTTP. Las fechas límite se validan en la factory `Task.crear` (`src/main/java/com/taskflow/model/Task.java`).
