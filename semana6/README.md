# Proyecto final · Semana 6 · GitHub Copilot

**Alumno:** `Jonathan Daniel Reyes Gordillo` · **Usuario de GitHub:** `danielrousse`

## 1. Qué construí

| | Feature | Especificación |
|---|---|---|
| [x] | `PATCH /tasks/{id}/assignee` — cambiar el responsable | [`specs/assignee.md`](../specs/assignee.md) |

## 2. El pull request

- **URL del PR (mergeado):** https://github.com/DanielRousse/taskflow-copilot-danielrousse/pull/5
- **Commit del merge en `main`:** `cb092fe Merge pull request #5 from DanielRousse/feature/assignee`
- **Comentarios de Copilot code review:** `0` (`Approval recommended · No open issues`)

## 3. Cómo lo hice

| Paso | Qué hice | Evidencia |
|---|---|---|
| Rama y spec | `git switch -c feature/assignee` y copié la spec a `specs/` | `git log --oneline main..feature/assignee` (antes del merge) |
| Implementación | `copilot -p "/crear-endpoint-taskflow …"` con `gpt-5-mini` | `semana6/sesion-implementacion.md` (tiene la línea `Skill "crear-endpoint-taskflow" loaded successfully`) |
| Revisión | agente `revisor` sobre `semana6/proyecto-final.diff` | `semana6/revision.md` (termina con `Veredicto:`) |
| Tests | `mvn test` en verde | `[INFO] Tests run: 83, Failures: 0, Errors: 0, Skipped: 0` |
| Comprobación REST | `verificar.ps1` con `casos-assignee.ps1` | sección 5 de este documento |
| Code review | Copilot en el PR | la pestaña *Files changed* del PR |

## 4. Qué hizo el agente y qué corregí yo

| # | Qué hizo mal el agente (archivo) | Quién lo detectó | Cómo quedó corregido |
|---|---|---|---|
| 1 | Usó nombres de clase con paquete completo sin `import` (`com.taskflow.dto.TaskAssigneeUpdateRequest` en `TaskController.java` y `com.taskflow.exception.TaskStateException` en `ReasignarTareaControllerTest.java`) | `revisor` (Sugerencia #1 en `semana6/revision.md`) y lectura del diff | Cambio a mano agregando los `import` y usando los nombres simples, actualización de `semana6/proyecto-final.diff` y commit `da25431 fix: hallazgos del revisor` |
| 2 | En la primera versión de `ReasignarTareaControllerTest.java` usó el título `"T4"` (2 caracteres), lo que disparó `TaskValidationException` en el constructor de `Task` | `mvn test` durante la sesión de implementación | El propio agente leyó el fallo de Surefire y cambió el título a `"Tarea 4"` antes de cerrar el turno (`334eae0 feat: assignee con la skill crear-endpoint-taskflow`) |

**Lo que el agente hizo bien a la primera** (una o dos líneas): Creó `TaskAssigneeUpdateRequest` con `@NotNull` y `@Positive`, implementó `TaskService.reasignar` rechazando tareas `DONE` con `TaskStateException`, expuso `PATCH /tasks/{id}/assignee` en `TaskController` resolviendo el `404` igual que la ruta vecina y escribió los 6 tests en las 2 clases nuevas (`ReasignarTareaServiceTest` con `ArgumentCaptor` y `ReasignarTareaControllerTest` con `@MockitoBean`) sin tocar ningún test existente.

## 5. Comprobaciones REST

```text
Repositorio: C:\Users\Lenovo\taskflow-copilot-danielrousse
URL de la app: http://127.0.0.1:8080
Empaquetando con Maven (mvn -q package -DskipTests), tarda unos segundos...
App arrancando (PID 15216). Esperando a que /info responda...
App lista en 8 s.
[OK]    GET /tasks/overdue devuelve solo la tarea 7
[OK]    GET /tasks/unassigned devuelve las tareas 4 y 6
[OK]    GET /projects/1/summary
[OK]    GET /projects/2/summary
[OK]    GET /projects/3/summary
[OK]    GET /projects/99/summary responde 404
[OK]    GET /projects/1/summary sin token responde 401
[OK]    PATCH /tasks/4/assignee asigna a luis
[OK]    GET /tasks/4 conserva el responsable nuevo
[OK]    PATCH /tasks/4/status a DONE ahora responde 200
[OK]    PATCH /tasks/2/assignee (DONE) responde 422
[OK]    PATCH /tasks/99/assignee responde 404
[OK]    PATCH /tasks/6/assignee con {} responde 400 y nombra assigneeId
[OK]    PATCH /tasks/6/assignee con 0 responde 400
[OK]    PATCH /tasks/6/assignee sin token responde 401
App detenida (PID 15216).
[OK]    App apagada: el puerto 8080 ya no responde
RESULTADO: 16/16 OK
```

## 6. Créditos de la semana

| Qué | AI credits |
|---|---|
| Usados en octubre según github.com (incluye semanas anteriores si usaste Copilot antes) | `151` |
| Implementación con la skill (`AI Credits` del PF-2) | `6.15` |
| Revisión del `revisor` (`AI Credits` del PF-3) | `0.81` |
| Correcciones del PF-4 y del PF-6, si hubo (`AI Credits`) | `0` |
