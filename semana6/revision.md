# Copilot CLI Session

> [!NOTE]
> - **Session ID:** `fd521a8b-d0f0-4cde-9fc0-fdad805b5c34`  
> - **Started:** 17/10/2026, 11:15:58 a.m.  
> - **Duration:** 27s  
> - **Exported:** 17/10/2026, 11:16:26 a.m.  

---

<sub>2s</sub>

### User

Revisa semana6/proyecto-final.diff contra la especificación specs/assignee.md.

---

<sub>8s</sub>

### Copilot

Voy a abrir en paralelo el diff solicitado y la especificación para revisarlos punto por punto: leeré semana6/proyecto-final.diff y specs/assignee.md para comparar alcance, contrato, tests y convenciones. Ejecutando lecturas en paralelo.

---

<sub>8s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\semana6\proyecto-final.diff.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\semana6\proyecto-final.diff

<details>
<summary>216 lines</summary>

```diff
diff --git a/src/main/java/com/taskflow/controller/TaskController.java b/src/main/java/com/taskflow/controller/TaskController.java
index 83670c0..8684308 100644
--- a/src/main/java/com/taskflow/controller/TaskController.java
+++ b/src/main/java/com/taskflow/controller/TaskController.java
@@ -155,4 +155,19 @@ public class TaskController {
         Task actualizada = taskService.cambiarStatus(id, request.status());
         return TaskMapper.aResponse(actualizada);
     }
+
+    /**
+     * PATCH /tasks/{id}/assignee — cambia solo el responsable (assigneeId).
+     * 404 si no existe; 422 si la tarea está DONE; 400 por Bean Validation si falta/invalid assigneeId.
+     */
+    @Operation(summary = "Reasigna el responsable de una tarea",
+            description = "Cambia solo el assigneeId. 404 si la tarea no existe; 422 si está DONE.")
+    @PatchMapping("/tasks/{id}/assignee")
+    public TaskResponse patchAssignee(@PathVariable("id") Long id,
+                                      @Valid @RequestBody com.taskflow.dto.TaskAssigneeUpdateRequest request) {
+        Task task = taskService.buscarPorId(id)
+                .orElseThrow(() -> new TaskNotFoundException(id));
+        Task actualizada = taskService.reasignar(task, request.assigneeId());
+        return TaskMapper.aResponse(actualizada);
+    }
 }
diff --git a/src/main/java/com/taskflow/dto/TaskAssigneeUpdateRequest.java b/src/main/java/com/taskflow/dto/TaskAssigneeUpdateRequest.java
new file mode 100644
index 0000000..393fd44
--- /dev/null
+++ b/src/main/java/com/taskflow/dto/TaskAssigneeUpdateRequest.java
@@ -0,0 +1,15 @@
+package com.taskflow.dto;
+
+import jakarta.validation.constraints.NotNull;
+import jakarta.validation.constraints.Positive;
+
+/**
+ * TaskAssigneeUpdateRequest — DTO de entrada para PATCH /tasks/{id}/assignee.
+ * Valida que assigneeId exista y sea positivo.
+ */
+public record TaskAssigneeUpdateRequest(
+        @NotNull
+        @Positive
+        Long assigneeId
+) {
+}
diff --git a/src/main/java/com/taskflow/service/TaskService.java b/src/main/java/com/taskflow/service/TaskService.java
index 5b318af..f58c2f6 100644
--- a/src/main/java/com/taskflow/service/TaskService.java
+++ b/src/main/java/com/taskflow/service/TaskService.java
@@ -86,6 +86,18 @@ public class TaskService {
         return repository.save(task);
     }
 
+    /**
+     * Reasigna el responsable de una tarea. Recibe la tarea ya encontrada; lanza TaskStateException si
+     * la tarea está DONE (422). Guarda y devuelve la entidad actualizada.
+     */
+    public Task reasignar(Task tarea, Long assigneeId) {
+        if (tarea.getStatus() == TaskStatus.DONE) {
+            throw new TaskStateException("No se puede reasignar una tarea terminada.");
+        }
+        tarea.setAssigneeId(assigneeId);
+        return repository.save(tarea);
+    }
+
     /**
      * Completa una tarea (la pasa a DONE). DELEGA en cambiarStatus(id, DONE): un
      * solo camino para "cambiar de estado", que además hereda la traducción a 422.
diff --git a/src/test/java/com/taskflow/slice/ReasignarTareaControllerTest.java b/src/test/java/com/taskflow/slice/ReasignarTareaControllerTest.java
new file mode 100644
index 0000000..41840a5
--- /dev/null
+++ b/src/test/java/com/taskflow/slice/ReasignarTareaControllerTest.java
@@ -0,0 +1,87 @@
+package com.taskflow.slice;
+
+import com.taskflow.controller.TaskController;
+import com.taskflow.model.Priority;
+import com.taskflow.model.Task;
+import com.taskflow.model.TaskStatus;
+import com.taskflow.security.JwtAuthenticationFilter;
+import com.taskflow.service.ProjectService;
+import com.taskflow.service.TaskService;
+import org.junit.jupiter.api.Test;
+import org.springframework.beans.factory.annotation.Autowired;
+import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
+import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
+import org.springframework.http.MediaType;
+import org.springframework.test.context.bean.override.mockito.MockitoBean;
+import org.springframework.test.web.servlet.MockMvc;
+
+import java.util.Optional;
+
+import static org.mockito.ArgumentMatchers.any;
+import static org.mockito.ArgumentMatchers.eq;
+import static org.mockito.Mockito.*;
+import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
+import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
+import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
+
+@WebMvcTest(TaskController.class)
+@AutoConfigureMockMvc(addFilters = false)
+class ReasignarTareaControllerTest {
+
+    @Autowired
+    private MockMvc mockMvc;
+
+    @MockitoBean
+    private TaskService taskService;
+
+    @MockitoBean
+    private ProjectService projectService;
+
+    @MockitoBean
+    private JwtAuthenticationFilter jwtAuthenticationFilter;
+
+    @Test
+    void patchAssignee_existente_devuelve200YJackson() throws Exception {
+        when(taskService.buscarPorId(4L)).thenReturn(Optional.of(new Task(4L, "Tarea 4", "d", TaskStatus.TODO, Priority.MED, 1L, null, null)));
+        when(taskService.reasignar(any(Task.class), eq(2L))).thenReturn(new Task(4L, "Tarea 4", "d", TaskStatus.TODO, Priority.MED, 1L, 2L, null));
+
+        mockMvc.perform(patch("/tasks/4/assignee")
+                        .contentType(MediaType.APPLICATION_JSON)
+                        .content("{\"assigneeId\":2}"))
+                .andExpect(status().isOk())
+                .andExpect(jsonPath("$.assigneeId").value(2));
+    }
+
+    @Test
+    void patchAssignee_tareaDone_devuelve422() throws Exception {
+        when(taskService.buscarPorId(2L)).thenReturn(Optional.of(new Task(2L, "Tarea 2", "d", TaskStatus.DONE, Priority.HIGH, 1L, 1L, null)));
+        when(taskService.reasignar(any(Task.class), anyLong())).thenThrow(new com.taskflow.exception.TaskStateException("No se puede reasignar una tarea terminada."));
+
+        mockMvc.perform(patch("/tasks/2/assignee")
+                        .contentType(MediaType.APPLICATION_JSON)
+                        .content("{\"assigneeId\":3}"))
+                .andExpect(status().isUnprocessableEntity())
+                .andExpect(jsonPath("$.status").value(422));
+    }
+
+    @Test
+    void patchAssignee_inexistente_devuelve404() throws Exception {
+        when(taskService.buscarPorId(99L)).thenReturn(Optional.empty());
+
+        mockMvc.perform(patch("/tasks/99/assignee")
+                        .contentType(MediaType.APPLICATION_JSON)
+                        .content("{\"assigneeId\":2}"))
+                .andExpect(status().isNotFound())
+                .andExpect(jsonPath("$.status").value(404));
+    }
+
+    @Test
+    void patchAssignee_cuerpoVacio_devuelve400YNoLlamaService() throws Exception {
+        mockMvc.perform(patch("/tasks/6/assignee")
+                        .contentType(MediaType.APPLICATION_JSON)
+                        .content("{}"))
+                .andExpect(status().isBadRequest());
+
+        verify(taskService, never()).reasignar(any(), anyLong());
+    }
+}
diff --git a/src/test/java/com/taskflow/unit/ReasignarTareaServiceTest.java b/src/test/java/com/taskflow/unit/ReasignarTareaServiceTest.java
new file mode 100644
index 0000000..2b61b0d
--- /dev/null
+++ b/src/test/java/com/taskflow/unit/ReasignarTareaServiceTest.java
@@ -0,0 +1,50 @@
+package com.taskflow.unit;
+
+import com.taskflow.model.Priority;
+import com.taskflow.model.Task;
+import com.taskflow.model.TaskStatus;
+import com.taskflow.repository.TaskRepository;
+import com.taskflow.service.TaskService;
+import com.taskflow.exception.TaskStateException;
+import com.taskflow.exception.TaskValidationException;
+import org.junit.jupiter.api.Test;
+import org.junit.jupiter.api.extension.ExtendWith;
+import org.mockito.ArgumentCaptor;
+import org.mockito.InjectMocks;
+import org.mockito.Mock;
+import org.mockito.junit.jupiter.MockitoExtension;
+
+import static org.junit.jupiter.api.Assertions.assertEquals;
+import static org.junit.jupiter.api.Assertions.assertThrows;
+import static org.mockito.Mockito.*;
+
+@ExtendWith(MockitoExtension.class)
+class ReasignarTareaServiceTest {
+
+    @Mock
+    private TaskRepository taskRepository;
+
+    @InjectMocks
+    private TaskService service;
+
+    @Test
+    void reasignar_tareaTodo_savesWithNewAssignee() throws TaskValidationException {
+        Task tarea = new Task(1L, "Tarea 1", "d", TaskStatus.TODO, Priority.MED, 1L, null, null);
+        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));
+
+        Task resultado = service.reasignar(tarea, 2L);
+
+        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
+        verify(taskRepository).save(captor.capture());
+        assertEquals(2L, captor.getValue().getAssigneeId());
+        assertEquals(2L, resultado.getAssigneeId());
+    }
+
+    @Test
+    void reasignar_tareaDone_throwsAndDoesNotSave() throws TaskValidationException {
+        Task tarea = new Task(2L, "Tarea 2", "d", TaskStatus.DONE, Priority.HIGH, 1L, 1L, null);
+
+        assertThrows(TaskStateException.class, () -> service.reasignar(tarea, 3L));
+        verify(taskRepository, never()).save(any());
+    }
+}
```

</details>


---

<sub>8s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\specs\assignee.md.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\specs\assignee.md

<details>
<summary>73 lines</summary>

````
# Especificación — `PATCH /tasks/{id}/assignee`

## Qué

Un endpoint que cambia **solo** el responsable de una tarea. Hoy la única forma es `PUT /tasks/{id}`,
que obliga a mandar la tarea completa (título, prioridad, fecha…) para tocar un campo. Es el mismo
razonamiento de `PATCH /tasks/{id}/status`: un DTO chico por operación.

```
PATCH /tasks/4/assignee
Authorization: ******
Content-Type: application/json

{ "assigneeId": 2 }
```

## Reglas

1. La tarea no existe → **`404`** con el `ErrorResponse` uniforme (`TaskNotFoundException`), resuelto
   **como en la ruta vecina** `GET /tasks/{id}`:
   `taskService.buscarPorId(id).orElseThrow(() -> new TaskNotFoundException(id))`.
2. La tarea está `DONE` → **`422`** con el `ErrorResponse` uniforme y el mensaje
   `No se puede reasignar una tarea terminada.`. Se lanza `TaskStateException`, la misma que usa
   `cambiarStatus` para su 422. Una tarea terminada conserva a quien la terminó.
3. El cuerpo no trae `assigneeId`, lo trae en `null` o trae un número menor que 1 → **`400`** por
   Bean Validation (`@NotNull` y `@Positive` en el DTO, `@Valid` en el controller). El
   `GlobalExceptionHandler` ya responde ese 400 con el detalle por campo en `errors`.
4. En cualquier otro caso: se asigna el responsable con `Task.setAssigneeId`, se guarda con el
   repositorio y responde **`200`** con el `TaskResponse` actualizado (vía `TaskMapper.aResponse`).
5. Seguridad: igual que el resto de `/tasks` (sin token → `401`). No cambies `SecurityConfig`.

**Fuera de alcance** (no lo implementes aunque parezca buena idea):

- Comprobar que el usuario `assigneeId` existe. Exigiría inyectar `UserRepository` en `TaskService`,
  y los tests existentes de `TaskService` no lo conocen.
- Quitar el responsable (`assigneeId: null`). Hoy es un 400 a propósito.

## Dónde

- DTO de entrada nuevo en `com.taskflow.dto`:
  `public record TaskAssigneeUpdateRequest(@NotNull @Positive Long assigneeId)`, con su Javadoc, en la
  misma forma que `TaskStatusUpdateRequest`.
- **Sin mapper nuevo**: la respuesta es el `TaskResponse` de siempre.
- Método nuevo en `TaskService`: `public Task reasignar(Task tarea, Long assigneeId)`. Recibe la
  tarea **ya encontrada** (el 404 lo resuelve el controller) y lanza `TaskStateException` si está
  `DONE`.
- Endpoint nuevo en `TaskController`: `@PatchMapping("/tasks/{id}/assignee")`, con
  `@PathVariable("id") Long id` y `@Valid @RequestBody TaskAssigneeUpdateRequest request`.

## Tests que deben existir al terminar

Los dos en **clases nuevas** (las que ya existen no se tocan):

- **Unit** `src/test/java/com/taskflow/unit/ReasignarTareaServiceTest.java`, con
  `@Mock TaskRepository` e `@InjectMocks TaskService`:
  - tarea `TODO` sin responsable → se llama a `save` con la tarea ya con el `assigneeId` pedido
    (compruébalo con `ArgumentCaptor`, no con lo que devuelve el mock de `save`);
  - tarea `DONE` → `TaskStateException` y `save` **nunca** se llama.
- **Slice** `src/test/java/com/taskflow/slice/ReasignarTareaControllerTest.java`, con
  `@WebMvcTest(TaskController.class)`, `@AutoConfigureMockMvc(addFilters = false)` y `@MockitoBean`
  de `TaskService`, `ProjectService` y `JwtAuthenticationFilter`:
  - `200` y `$.assigneeId` = el pedido;
  - `422` cuando `reasignar` lanza `TaskStateException`;
  - `404` cuando `buscarPorId` devuelve `Optional.empty()`;
  - `400` con el cuerpo `{}`, y `reasignar` **nunca** se llama (`verify(taskService, never())`).

## Resultado esperado con la semilla

Con la app recién arrancada con el perfil `h2` y el token de `ana`, **en este orden** (el paso 3
depende del 1):

| # | Petición | Resultado |
|---|---|---|
| 1 | `PATCH /tasks/4/assignee` con `{"assigneeId": 2}` | `200`, `assigneeId` = 2 (la tarea 4 no tenía responsable) |
| 2 | `GET /tasks/4` | `assigneeId` = 2: se guardó, no solo se respondió |
| 3 | `PATCH /tasks/4/status` con `{"status": "DONE"}` | `200`. Antes del paso 1 respondía `422` («sin responsable»): la regla de `Task.setStatus` ahora deja pasar |
| 4 | `PATCH /tasks/2/assignee` con `{"assigneeId": 3}` | `422` con `message` = `No se puede reasignar una tarea terminada.` (la 2 es `DONE`) |
| 5 | `PATCH /tasks/99/assignee` con `{"assigneeId": 2}` | `404` |
| 6 | `PATCH /tasks/6/assignee` con `{}` | `400`, y en `errors` aparece `assigneeId` |
| 7 | `PATCH /tasks/6/assignee` con `{"assigneeId": 0}` | `400` |
| 8 | el paso 1 sin token | `401` |

## Restricciones para el agente

- No modifiques, borres ni desactives ningún test existente.
- No toques archivos fuera de `TaskAssigneeUpdateRequest.java` (nuevo), `TaskService.java`,
  `TaskController.java` y las dos clases de test nuevas.
- Al terminar, `mvn -q test` tiene que pasar completo.

El pull request del proyecto final lleva además `semana6/`, `.github/skills/verificar-taskflow/casos-assignee.ps1`
y una línea nueva en `verificar.ps1`. Esos archivos los agregas tú en el proceso (PF-2 a PF-7 de la guía),
no el agente: no cuentan contra esta restricción.
````

</details>


---

<sub>27s</sub>

### Copilot

Resumen breve: comparé el diff con la especificación punto por punto. No hay incumplimientos funcionales; los tests exigidos están presentes y bien construidos. Recomendación menor incluida.

| # | Gravedad | Archivo:línea | Punto de la checklist | Qué pasa | Qué harías |
|---|---|---|---|---|---|
| 1 | Sugerencia | src/main/java/com/taskflow/controller/TaskController.java (método añad.) | Convenciones / claridad | El parámetro del controller usa el nombre de clase con paquete fully-qualified en la firma (@Valid @RequestBody com.taskflow.dto.TaskAssigneeUpdateRequest). Compila pero reduce legibilidad. | Importar TaskAssigneeUpdateRequest y usar el simple nombre en la firma. |

Casos sin test: ninguno.

Veredicto: APROBADO.

---

<sub>Generated by [GitHub Copilot CLI](https://github.com/features/copilot/cli)</sub>
