# Copilot CLI Session

> [!NOTE]
> - **Session ID:** `6c0e7e05-b9ac-4799-b9a7-da89e27933e1`  
> - **Started:** 16/10/2026, 12:41:28 p.m.  
> - **Duration:** 53s  
> - **Exported:** 16/10/2026, 12:42:22 p.m.  

---

<sub>2s</sub>

### User

Revisa evidencia/dia4/summary.diff contra la especificaciÃ³n specs/summary.md.

---

<sub>7s</sub>

### Copilot

Abriendo el diff (evidence/dia4/summary.diff) y la especificaciÃ³n (specs/summary.md) para comparar alcance y cambios; tras leer el diff abrirÃ© los archivos afectados. Ejecutando lecturas en paralelo.

---

<sub>7s</sub>

### `view` â€” Failed

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\evidence\dia4\summary.diff.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\evidence\dia4\summary.diff

```
Path does not exist
```


---

<sub>8s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\specs\summary.md.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\specs\summary.md

<details>
<summary>52 lines</summary>

````
## QuÃ© se pide

Un endpoint nuevo, `GET /projects/{id}/summary`, que resume un proyecto: **cuÃ¡ntas tareas tiene en
cada estado y cuÃ¡ntas estÃ¡n vencidas**. Sirve para pintar un tablero sin descargar todas las tareas del
proyecto. Pide el token de `POST /auth/login`, como todo lo que no es pÃºblico.

## Respuesta 200

```json
{
  "projectId": 2,
  "projectName": "App MÃ³vil",
  "totalTasks": 4,
  "byStatus": { "TODO": 1, "IN_PROGRESS": 2, "DONE": 1 },
  "overdue": 1
}
```

| Campo | Tipo | QuÃ© es |
|---|---|---|
| `projectId` | nÃºmero | el `id` del proyecto pedido |
| `projectName` | texto | el `name` del proyecto |
| `totalTasks` | nÃºmero | cuÃ¡ntas tareas tiene el proyecto |
| `byStatus` | objeto | una clave por cada valor de `TaskStatus` (`TODO`, `IN_PROGRESS`, `DONE`), **siempre las tres**, aunque valgan 0 |
| `overdue` | nÃºmero | cuÃ¡ntas tareas del proyecto cumplen `Task.estaVencida()` |

## Reglas

1. **Vencida** es exactamente lo que ya dice `Task.estaVencida()`: tiene fecha lÃ­mite, la fecha ya pasÃ³ y
   el estado no es `DONE`. Se reutiliza ese mÃ©todo; no se reescribe la regla en el servicio.
2. Un proyecto **sin tareas** responde 200 con `totalTasks` 0, las tres claves de `byStatus` en 0 y `overdue` 0.
3. Un proyecto **que no existe** responde **404** con el `ErrorResponse` uniforme de siempre
   (`ProjectNotFoundException`, igual que `GET /projects/{id}`).
4. **Sin token** responde **401**, como todo lo que no es pÃºblico.
5. Cualquier usuario autenticado puede pedir el resumen de cualquier proyecto (igual que `GET /projects/{id}/tasks`).

## Resultado esperado con la semilla del perfil `h2`

| PeticiÃ³n | `totalTasks` | `byStatus` | `overdue` |
|---|---|---|---|
| `GET /projects/1/summary` | 5 | `TODO` 3 Â· `IN_PROGRESS` 1 Â· `DONE` 1 | 0 (la tarea 2 tiene fecha pasada, pero estÃ¡ `DONE`) |
| `GET /projects/2/summary` | 4 | `TODO` 1 Â· `IN_PROGRESS` 2 Â· `DONE` 1 | 1 (la tarea 7) |
| `GET /projects/3/summary` | 0 | `TODO` 0 Â· `IN_PROGRESS` 0 Â· `DONE` 0 | 0 |
| `GET /projects/99/summary` | â€” | â€” | 404 |

Si antes de probar creaste tareas en un proyecto (por la UI o con el servidor MCP), `totalTasks` y
`TODO` de ese proyecto suben en la misma cantidad. Al reiniciar la app con el perfil `h2` vuelven a
ser los de la tabla.

## CÃ³mo se construye (convenciones de TaskFlow)

- DTO de salida: un `record` en `com.taskflow.dto`, `ProjectSummaryResponse`.
- La lÃ³gica de conteo va en el servicio (`ProjectService`), no en el controller.
- El controller solo traduce HTTP: busca el proyecto (404 si no existe) y delega.
- Tests:
  - **unit** del servicio con Mockito: un proyecto con tareas en los tres estados y una vencida; un proyecto sin tareas.
  - **slice** `@WebMvcTest` del controller: 200 con el JSON de arriba y 404 con un proyecto inexistente.
- `mvn -q test` en verde al terminar, sin modificar tests existentes.

## Criterios de aceptaciÃ³n

- [ ] `GET /projects/2/summary` con el token de `ana` devuelve el JSON del ejemplo.
- [ ] `GET /projects/3/summary` devuelve ceros en los cinco nÃºmeros.
- [ ] `GET /projects/99/summary` devuelve 404.
- [ ] `mvn -q test` en verde: los tests de antes mÃ¡s los nuevos.
````

</details>


---

<sub>13s</sub>

### Copilot

BuscarÃ© el diff bajo los directorios posibles y abrirÃ© el archivo de diff y la especificaciÃ³n para revisar cambios contra la spec. Ejecutando listados en paralelo.

---

<sub>13s</sub>

### `view` â€” Failed

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\evidence.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\evidence (lines 1-end)

```
Path does not exist
```


---

<sub>13s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\evidencia.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\evidencia (lines 1-end)

```
dia1
dia2
dia3
dia4
```


---

<sub>20s</sub>

### Copilot

AbrirÃ© el diff en evidencia/dia4 y listarÃ© el contenido del directorio para localizar los archivos modificados; esto permite luego abrir cada archivo afectado segÃºn el diff. Ejecutando las lecturas en paralelo.

---

<sub>20s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\evidencia\dia4\summary.diff.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\evidencia\dia4\summary.diff

<details>
<summary>265 lines</summary>

```diff
diff --git a/src/main/java/com/taskflow/controller/ProjectController.java b/src/main/java/com/taskflow/controller/ProjectController.java
index bef609e..305786a 100644
--- a/src/main/java/com/taskflow/controller/ProjectController.java
+++ b/src/main/java/com/taskflow/controller/ProjectController.java
@@ -10,6 +10,7 @@ import com.taskflow.model.Project;
 import com.taskflow.model.Task;
 import com.taskflow.model.TaskStatus;
 import com.taskflow.service.ProjectService;
+import com.taskflow.dto.ProjectSummaryResponse;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.tags.Tag;
 import jakarta.validation.Valid;
@@ -86,6 +87,19 @@ public class ProjectController {
         return tareas.stream().map(TaskMapper::aResponse).toList();
     }
 
+    /**
+     * GET /projects/{id}/summary â€” resumen del proyecto: totales por estado y vencidas.
+     * 404 si el proyecto no existe (ProjectNotFoundException via advice).
+     */
+    @Operation(summary = "Resumen del proyecto",
+            description = "Devuelve totales por estado y cuÃ¡ntas tareas estÃ¡n vencidas. 404 si el proyecto no existe.")
+    @GetMapping("/projects/{id}/summary")
+    public ProjectSummaryResponse getProjectSummary(@PathVariable("id") Long id) {
+        Project proyecto = projectService.buscarPorId(id)
+                .orElseThrow(() -> new com.taskflow.exception.ProjectNotFoundException(id));
+        return projectService.resumen(proyecto);
+    }
+
     /**
      * POST /projects â€” 201 + Location a /projects/{id}. @Valid dispara Bean Validation (400 si falla).
      * El owner sale del JWT â€” el Authentication (inyectado por Spring Security) trae el username
diff --git a/src/main/java/com/taskflow/dto/ProjectSummaryResponse.java b/src/main/java/com/taskflow/dto/ProjectSummaryResponse.java
new file mode 100644
index 0000000..9bc0139
--- /dev/null
+++ b/src/main/java/com/taskflow/dto/ProjectSummaryResponse.java
@@ -0,0 +1,18 @@
+package com.taskflow.dto;
+
+import java.util.Map;
+
+/**
+ * ProjectSummaryResponse â€” contrato de salida para GET /projects/{id}/summary.
+ *
+ * Contiene los totales del proyecto por estado y el conteo de tareas vencidas. Las claves de
+ * byStatus son los nombres de los enums de TaskStatus ("TODO","IN_PROGRESS","DONE").
+ */
+public record ProjectSummaryResponse(
+        Long projectId,
+        String projectName,
+        int totalTasks,
+        Map<String, Integer> byStatus,
+        int overdue
+) {
+}
diff --git a/src/main/java/com/taskflow/mapper/ProjectMapper.java b/src/main/java/com/taskflow/mapper/ProjectMapper.java
index 38f44a0..5e931a5 100644
--- a/src/main/java/com/taskflow/mapper/ProjectMapper.java
+++ b/src/main/java/com/taskflow/mapper/ProjectMapper.java
@@ -21,4 +21,21 @@ public final class ProjectMapper {
         return new ProjectResponse(p.getId(), p.getName(), p.getDescription(),
                 p.getOwnerId(), p.getCreatedAt());
     }
+
+    /**
+     * Construye el ProjectSummaryResponse a partir de la entidad y los conteos calculados en el
+     * service. Convierte las claves de TaskStatus a String para el DTO.
+     */
+    public static com.taskflow.dto.ProjectSummaryResponse aSummary(Project p,
+                                                                   java.util.Map<com.taskflow.model.TaskStatus, Integer> byStatusEnum,
+                                                                   int totalTasks,
+                                                                   int overdue) {
+        java.util.Map<String, Integer> byStatus = new java.util.HashMap<>();
+        // Garantizar las tres claves siempre presentes
+        for (com.taskflow.model.TaskStatus s : com.taskflow.model.TaskStatus.values()) {
+            byStatus.put(s.name(), byStatusEnum.getOrDefault(s, 0));
+        }
+        return new com.taskflow.dto.ProjectSummaryResponse(
+                p.getId(), p.getName(), totalTasks, byStatus, overdue);
+    }
 }
diff --git a/src/main/java/com/taskflow/service/ProjectService.java b/src/main/java/com/taskflow/service/ProjectService.java
index b3e9d0f..fe9e086 100644
--- a/src/main/java/com/taskflow/service/ProjectService.java
+++ b/src/main/java/com/taskflow/service/ProjectService.java
@@ -99,4 +99,28 @@ public class ProjectService {
         tareasDe(id).forEach(t -> taskRepository.deleteById(t.getId()));   // cascada manual (la FK obliga el orden)
         projectRepository.deleteById(id);
     }
+
+    /**
+     * Calcula el resumen de un proyecto: totales por estado, total de tareas y vencidas.
+     * Recibe la entidad Project ya validada por el controller.
+     */
+    public com.taskflow.dto.ProjectSummaryResponse resumen(Project project) {
+        Long projectId = project.getId();
+        java.util.List<com.taskflow.model.Task> tareas = taskRepository.findByProjectId(projectId);
+
+        int totalTasks = tareas.size();
+
+        java.util.Map<com.taskflow.model.TaskStatus, Integer> byStatus = new java.util.EnumMap<>(com.taskflow.model.TaskStatus.class);
+        // Inicializar a cero
+        for (com.taskflow.model.TaskStatus s : com.taskflow.model.TaskStatus.values()) {
+            byStatus.put(s, 0);
+        }
+        for (com.taskflow.model.Task t : tareas) {
+            byStatus.put(t.getStatus(), byStatus.getOrDefault(t.getStatus(), 0) + 1);
+        }
+
+        int overdue = (int) tareas.stream().filter(com.taskflow.model.Task::estaVencida).count();
+
+        return com.taskflow.mapper.ProjectMapper.aSummary(project, byStatus, totalTasks, overdue);
+    }
 }
diff --git a/src/test/java/com/taskflow/slice/ProjectControllerSummaryTest.java b/src/test/java/com/taskflow/slice/ProjectControllerSummaryTest.java
new file mode 100644
index 0000000..5c6fbd6
--- /dev/null
+++ b/src/test/java/com/taskflow/slice/ProjectControllerSummaryTest.java
@@ -0,0 +1,63 @@
+package com.taskflow.slice;
+
+import com.taskflow.controller.ProjectController;
+import com.taskflow.dto.ProjectSummaryResponse;
+import com.taskflow.model.Project;
+import com.taskflow.security.JwtAuthenticationFilter;
+import com.taskflow.service.ProjectService;
+import org.junit.jupiter.api.Test;
+import org.springframework.beans.factory.annotation.Autowired;
+import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
+import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
+import org.springframework.boot.test.mock.mockito.MockBean;
+import org.springframework.test.web.servlet.MockMvc;
+
+import java.time.LocalDate;
+import java.util.Optional;
+import java.util.Map;
+
+import static org.mockito.Mockito.when;
+import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
+import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
+import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
+
+@WebMvcTest(ProjectController.class)
+@AutoConfigureMockMvc(addFilters = false)
+public class ProjectControllerSummaryTest {
+
+    @Autowired
+    MockMvc mockMvc;
+
+    @MockBean
+    ProjectService projectService;
+
+    @MockBean
+    JwtAuthenticationFilter jwtAuthenticationFilter;
+
+    @Test
+    void getSummary_proyectoExistente_devuelve200yJSON() throws Exception {
+        Project p = new Project(2L, "App MÃ³vil", "desc", 1L, LocalDate.now());
+        ProjectSummaryResponse dto = new ProjectSummaryResponse(2L, "App MÃ³vil", 4, Map.of("TODO",1,"IN_PROGRESS",2,"DONE",1), 1);
+
+        when(projectService.buscarPorId(2L)).thenReturn(Optional.of(p));
+        when(projectService.resumen(p)).thenReturn(dto);
+
+        mockMvc.perform(get("/projects/2/summary"))
+                .andExpect(status().isOk())
+                .andExpect(jsonPath("$.projectId").value(2))
+                .andExpect(jsonPath("$.projectName").value("App MÃ³vil"))
+                .andExpect(jsonPath("$.totalTasks").value(4))
+                .andExpect(jsonPath("$.byStatus.TODO").value(1))
+                .andExpect(jsonPath("$.byStatus.IN_PROGRESS").value(2))
+                .andExpect(jsonPath("$.byStatus.DONE").value(1))
+                .andExpect(jsonPath("$.overdue").value(1));
+    }
+
+    @Test
+    void getSummary_proyectoInexistente_devuelve404() throws Exception {
+        when(projectService.buscarPorId(99L)).thenReturn(Optional.empty());
+
+        mockMvc.perform(get("/projects/99/summary"))
+                .andExpect(status().isNotFound());
+    }
+}
diff --git a/src/test/java/com/taskflow/unit/ProjectServiceSummaryTest.java b/src/test/java/com/taskflow/unit/ProjectServiceSummaryTest.java
new file mode 100644
index 0000000..bbc364f
--- /dev/null
+++ b/src/test/java/com/taskflow/unit/ProjectServiceSummaryTest.java
@@ -0,0 +1,75 @@
+package com.taskflow.unit;
+
+import com.taskflow.exception.ProjectNotFoundException;
+import com.taskflow.model.Priority;
+import com.taskflow.model.Project;
+import com.taskflow.model.Task;
+import com.taskflow.model.TaskStatus;
+import com.taskflow.repository.ProjectRepository;
+import com.taskflow.repository.TaskRepository;
+import com.taskflow.repository.UserRepository;
+import com.taskflow.service.ProjectService;
+import org.junit.jupiter.api.Test;
+import org.junit.jupiter.api.extension.ExtendWith;
+import org.mockito.InjectMocks;
+import org.mockito.Mock;
+import org.mockito.junit.jupiter.MockitoExtension;
+
+import java.time.LocalDate;
+import java.util.List;
+import java.util.Optional;
+
+import static org.junit.jupiter.api.Assertions.*;
+import static org.mockito.Mockito.when;
+
+@ExtendWith(MockitoExtension.class)
+public class ProjectServiceSummaryTest {
+
+    @Mock
+    ProjectRepository projectRepository;
+
+    @Mock
+    TaskRepository taskRepository;
+
+    @Mock
+    UserRepository userRepository;
+
+    @InjectMocks
+    ProjectService projectService;
+
+    @Test
+    void resumen_calculado_correctamente() throws Exception {
+        Project p = new Project(2L, "App MÃ³vil", "desc", 1L, LocalDate.now());
+        Task t1 = new Task(1L, "T01", "d", TaskStatus.TODO, Priority.MED, 2L, null, null);
+                Task t2 = new Task(2L, "T02", "d", TaskStatus.DONE, Priority.MED, 2L, 1L, LocalDate.now().minusDays(5)); // pasada pero DONE -> no cuenta
+                Task t3 = new Task(3L, "T03", "d", TaskStatus.IN_PROGRESS, Priority.MED, 2L, 1L, null);
+                Task t4 = new Task(4L, "T04", "d", TaskStatus.IN_PROGRESS, Priority.MED, 2L, null, LocalDate.now().minusDays(1)); // vencida
+
+        when(taskRepository.findByProjectId(2L)).thenReturn(List.of(t1, t2, t3, t4));
+
+        var resumen = projectService.resumen(p);
+
+        assertEquals(2L, resumen.projectId());
+        assertEquals("App MÃ³vil", resumen.projectName());
+        assertEquals(4, resumen.totalTasks());
+        assertEquals(1, resumen.byStatus().get("TODO").intValue());
+        assertEquals(2, resumen.byStatus().get("IN_PROGRESS").intValue());
+        assertEquals(1, resumen.byStatus().get("DONE").intValue());
+        assertEquals(1, resumen.overdue());
+    }
+
+    @Test
+    void resumen_proyectoSinTareas_devuelveCeros() {
+        Project p = new Project(3L, "Empty", "desc", 1L, LocalDate.now());
+        when(taskRepository.findByProjectId(3L)).thenReturn(List.of());
+
+        var resumen = projectService.resumen(p);
+
+        assertEquals(3L, resumen.projectId());
+        assertEquals(0, resumen.totalTasks());
+        assertEquals(0, resumen.byStatus().get("TODO").intValue());
+        assertEquals(0, resumen.byStatus().get("IN_PROGRESS").intValue());
+        assertEquals(0, resumen.byStatus().get("DONE").intValue());
+        assertEquals(0, resumen.overdue());
+    }
+}
\ No newline at end of file
```

</details>


---

<sub>20s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\evidencia\dia4.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\evidencia\dia4

```
summary-sesion.md
summary.diff
verificar-sesion.md
verificar.txt
```


---

<sub>30s</sub>

### Copilot

Abriendo los archivos modificados por el diff para comparar su contenido actual con la especificaciÃ³n: controller, service, mapper, dto y los tests nuevos. Ejecutando lecturas en paralelo.

---

<sub>30s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\src\main\java\com\taskflow\controller\ProjectController.java.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\src\main\java\com\taskflow\controller\ProjectController.java

<details>
<summary>136 lines</summary>

```
package com.taskflow.controller;

import com.taskflow.dto.ProjectRequest;
import com.taskflow.dto.ProjectResponse;
import com.taskflow.dto.TaskResponse;
import com.taskflow.exception.ProjectNotFoundException;
import com.taskflow.mapper.ProjectMapper;
import com.taskflow.mapper.TaskMapper;
import com.taskflow.model.Project;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.service.ProjectService;
import com.taskflow.dto.ProjectSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * ProjectController â€” la puerta HTTP de los proyectos: CRUD completo, en DTOs
 * (ProjectRequest / ProjectResponse); las tareas de un proyecto salen como TaskResponse.
 *
 * RepeticiÃ³n deliberada del patrÃ³n de TaskController: mismos gestos (201 + Location, @Valid, 404 vÃ­a
 * orElseThrow -> advice). Con el lado Project queda completa la tabla de endpoints de la API (menos /auth/*).
 */
@RestController
@Tag(name = "Projects", description = "CRUD de proyectos y sus tareas. Borrar un proyecto arrastra sus tareas (cascada).")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /** GET /projects â€” todos los proyectos como ProjectResponse. */
    @Operation(summary = "Lista todos los proyectos",
            description = "Devuelve todos los proyectos como ProjectResponse (lista vacÃ­a si no hay ninguno).")
    @GetMapping("/projects")
    public List<ProjectResponse> getProjects() {
        return projectService.listar().stream()
                .map(ProjectMapper::aResponse)
                .toList();
    }

    /** GET /projects/{id} â€” 200 con ProjectResponse; 404 uniforme si no existe. */
    @Operation(summary = "Obtiene un proyecto por id",
            description = "200 con el ProjectResponse; 404 uniforme si el id no existe.")
    @GetMapping("/projects/{id}")
    public ProjectResponse getProject(@PathVariable("id") Long id) {
        Project proyecto = projectService.buscarPorId(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        return ProjectMapper.aResponse(proyecto);
    }

    /**
     * GET /projects/{id}/tasks â€” las tareas de un proyecto como TaskResponse. DistinciÃ³n clave:
     * proyecto inexistente -> 404 (orElseThrow); proyecto sin tareas -> 200 con []. El filtro
     * ?status= es opcional (mismo enum que /tasks). Delega en ProjectService.tareasDe.
     */
    @Operation(summary = "Lista las tareas de un proyecto",
            description = "Tareas del proyecto como TaskResponse; 404 si el proyecto no existe, 200 con [] si no tiene tareas. Filtro opcional ?status=.")
    @GetMapping("/projects/{id}/tasks")
    public List<TaskResponse> getTareasDeProyecto(
            @PathVariable("id") Long id,
            @RequestParam(name = "status", required = false) TaskStatus status) {   // status: opcional
        projectService.buscarPorId(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        List<Task> tareas = projectService.tareasDe(id);
        if (status != null) {                          // filtro opcional por estado
            tareas = tareas.stream().filter(t -> t.getStatus() == status).toList();
        }
        return tareas.stream().map(TaskMapper::aResponse).toList();
    }

    /**
     * GET /projects/{id}/summary â€” resumen del proyecto: totales por estado y vencidas.
     * 404 si el proyecto no existe (ProjectNotFoundException via advice).
     */
    @Operation(summary = "Resumen del proyecto",
            description = "Devuelve totales por estado y cuÃ¡ntas tareas estÃ¡n vencidas. 404 si el proyecto no existe.")
    @GetMapping("/projects/{id}/summary")
    public ProjectSummaryResponse getProjectSummary(@PathVariable("id") Long id) {
        Project proyecto = projectService.buscarPorId(id)
                .orElseThrow(() -> new com.taskflow.exception.ProjectNotFoundException(id));
        return projectService.resumen(proyecto);
    }

    /**
     * POST /projects â€” 201 + Location a /projects/{id}. @Valid dispara Bean Validation (400 si falla).
     * El owner sale del JWT â€” el Authentication (inyectado por Spring Security) trae el username
     * del token; el service lo resuelve a ownerId. El dueÃ±o es QUIEN crea, no una constante.
     */
    @Operation(summary = "Crea un proyecto",
            description = "El owner se toma del usuario autenticado (el username del JWT).")
    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request,
                                                         Authentication authentication) {
        Project creado = projectService.crear(request, authentication.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/projects/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(ProjectMapper.aResponse(creado));
    }

    /** PUT /projects/{id} â€” reemplazo COMPLETO. 200 con ProjectResponse; 404 si no existe. */
    @Operation(summary = "Reemplaza un proyecto",
            description = "Conserva id, owner y createdAt; name y description vienen del cuerpo.")
    @PutMapping("/projects/{id}")
    public ProjectResponse updateProject(@PathVariable("id") Long id,
                                         @Valid @RequestBody ProjectRequest request) {
        Project actualizado = projectService.reemplazar(id, request);
        return ProjectMapper.aResponse(actualizado);
    }

    /**
     * DELETE /projects/{id} â€” 204 No Content; 404 si no existe. Borra en cascada las tareas del
     * proyecto (regla "no Task sin Project").
     *
     * La regla de negocio, aplicada: @PreAuthorize se evalÃºa ANTES del mÃ©todo: pasa si el
     * usuario es ADMIN o es el owner del proyecto (bean @projectSecurity, data-driven). Un USER que no
     * es owner -> 403 (la API SÃ sabe quiÃ©n es, por eso 403 y no 401). Proyecto inexistente ->
     * esOwner devuelve true a propÃ³sito para que hable el 404 del servicio.
     */
    @Operation(summary = "Borra un proyecto y sus tareas",
            description = "Solo ADMIN o el owner. Un USER no-owner recibe 403.")
    @PreAuthorize("hasRole('ADMIN') or @projectSecurity.esOwner(#id, authentication.name)")
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Long id) {
        projectService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
```

</details>


---

<sub>30s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\src\main\java\com\taskflow\dto\ProjectSummaryResponse.java.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\src\main\java\com\taskflow\dto\ProjectSummaryResponse.java

<details>
<summary>16 lines</summary>

```
package com.taskflow.dto;

import java.util.Map;

/**
 * ProjectSummaryResponse â€” contrato de salida para GET /projects/{id}/summary.
 *
 * Contiene los totales del proyecto por estado y el conteo de tareas vencidas. Las claves de
 * byStatus son los nombres de los enums de TaskStatus ("TODO","IN_PROGRESS","DONE").
 */
public record ProjectSummaryResponse(
        Long projectId,
        String projectName,
        int totalTasks,
        Map<String, Integer> byStatus,
        int overdue
) {
}
```

</details>


---

<sub>30s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\src\main\java\com\taskflow\mapper\ProjectMapper.java.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\src\main\java\com\taskflow\mapper\ProjectMapper.java

<details>
<summary>36 lines</summary>

```
package com.taskflow.mapper;

import com.taskflow.dto.ProjectResponse;
import com.taskflow.model.Project;

/**
 * ProjectMapper â€” puente DTO &lt;-&gt; dominio del lado Project. EstÃ¡tico, a mano, sin MapStruct.
 *
 * Mapeo trivial: la entidad guarda 'ownerId' directo (no un objeto User), asÃ­ que aResponse no
 * deriva el id desde un objeto â€” lee p.getOwnerId() tal cual. Cuando el
 * dominio coincide con el contrato de salida (ProjectResponse con ownerId Long), el mapeo es una lÃ­nea.
 */
public final class ProjectMapper {

    private ProjectMapper() {
        // no instanciable
    }

    /** Entidad -> DTO de salida. ownerId sale directo del campo (sin puente por objeto). */
    public static ProjectResponse aResponse(Project p) {
        return new ProjectResponse(p.getId(), p.getName(), p.getDescription(),
                p.getOwnerId(), p.getCreatedAt());
    }

    /**
     * Construye el ProjectSummaryResponse a partir de la entidad y los conteos calculados en el
     * service. Convierte las claves de TaskStatus a String para el DTO.
     */
    public static com.taskflow.dto.ProjectSummaryResponse aSummary(Project p,
                                                                   java.util.Map<com.taskflow.model.TaskStatus, Integer> byStatusEnum,
                                                                   int totalTasks,
                                                                   int overdue) {
        java.util.Map<String, Integer> byStatus = new java.util.HashMap<>();
        // Garantizar las tres claves siempre presentes
        for (com.taskflow.model.TaskStatus s : com.taskflow.model.TaskStatus.values()) {
            byStatus.put(s.name(), byStatusEnum.getOrDefault(s, 0));
        }
        return new com.taskflow.dto.ProjectSummaryResponse(
                p.getId(), p.getName(), totalTasks, byStatus, overdue);
    }
}
```

</details>


---

<sub>30s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\src\main\java\com\taskflow\service\ProjectService.java.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\src\main\java\com\taskflow\service\ProjectService.java

<details>
<summary>110 lines</summary>

```
package com.taskflow.service;

import com.taskflow.dto.ProjectRequest;
import com.taskflow.exception.ProjectNotFoundException;
import com.taskflow.model.Project;
import com.taskflow.model.Task;
import com.taskflow.model.User;
import com.taskflow.repository.ProjectRepository;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ProjectService â€” la capa de negocio del lado Project. Cero HTTP aquÃ­ (el "no existe" se traduce con
 * orElseThrow -> ProjectNotFoundException; el advice le pone el 404).
 *
 * Dos decisiones que se ven aquÃ­:
 *   - Project guarda un ownerId (Long), no un objeto User, asÃ­ que 'crear' y 'reemplazar' manejan
 *     ese Long. 'crear' construye con id null y la BD asigna el id (IDENTITY).
 *   - 'tareasDe' usa la derived query taskRepository.findByProjectId(id) en vez de filtrar findAll()
 *     con un stream: el filtro lo hace la BD.
 */
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;   // para resolver el owner desde el username del JWT

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /** Todos los proyectos. */
    public List<Project> listar() {
        return projectRepository.findAll();
    }

    /** Un proyecto por id (el Optional sube tal cual: el controller decide 404 vs 200). */
    public Optional<Project> buscarPorId(Long id) {
        return projectRepository.findById(id);
    }

    /**
     * Las tareas de un proyecto, con la derived query taskRepository.findByProjectId(projectId):
     * el WHERE project_id = ? lo hace
     * la BD (viajan solo las filas del proyecto). "no existe el proyecto" (404) lo decide el
     * controller con buscarPorId; ESTE mÃ©todo, si el proyecto existe pero no tiene tareas, devuelve
     * [] (-> 200 con []). No son lo mismo.
     */
    public List<Task> tareasDe(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    /**
     * Crea un proyecto (POST): el request trae name y description; el ownerId NO es una constante
     * â€” se resuelve del USERNAME AUTENTICADO (el que puso el JWT en el
     * Authentication). El dueÃ±o no lo decide el cliente ni una semilla: es QUIEN llama. createdAt = hoy;
     * id == null -> la BD lo asigna (IDENTITY).
     */
    public Project crear(ProjectRequest request, String ownerUsername) {
        User owner = userRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new UsernameNotFoundException(ownerUsername));
        Project nuevo = new Project(null, request.name(), request.description(),
                owner.getId(), LocalDate.now());
        return projectRepository.save(nuevo);
    }

    /**
     * Reemplazo COMPLETO (PUT): existe -> reconstruye con el MISMO id, conservando ownerId y createdAt
     * (el cliente no los toca); name y description vienen del request. save con id presente = update.
     * No existe -> 404.
     */
    public Project reemplazar(Long id, ProjectRequest request) {
        Project actual = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        Project reemplazo = new Project(actual.getId(), request.name(), request.description(),
                actual.getOwnerId(), actual.getCreatedAt());
        return projectRepository.save(reemplazo);
    }

    /**
     * Elimina un proyecto (DELETE) con CASCADA MANUAL: borra primero sus tareas (regla "no Task sin
     * Project" â€” y ademÃ¡s la FK task.project_id lo EXIGE: borrar el proyecto con tareas colgando darÃ­a
     * una violaciÃ³n de integridad) y luego el proyecto. No existe -> 404. La regla owner/ADMIN no
     * vive aquÃ­: la aplica el @PreAuthorize del controller antes de llegar a este mÃ©todo.
     */
    public void eliminar(Long id) {
        projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        tareasDe(id).forEach(t -> taskRepository.deleteById(t.getId()));   // cascada manual (la FK obliga el orden)
        projectRepository.deleteById(id);
    }

    /**
     * Calcula el resumen de un proyecto: totales por estado, total de tareas y vencidas.
     * Recibe la entidad Project ya validada por el controller.
     */
    public com.taskflow.dto.ProjectSummaryResponse resumen(Project project) {
        Long projectId = project.getId();
        java.util.List<com.taskflow.model.Task> tareas = taskRepository.findByProjectId(projectId);

        int totalTasks = tareas.size();

        java.util.Map<com.taskflow.model.TaskStatus, Integer> byStatus = new java.util.EnumMap<>(com.taskflow.model.TaskStatus.class);
        // Inicializar a cero
        for (com.taskflow.model.TaskStatus s : com.taskflow.model.TaskStatus.values()) {
            byStatus.put(s, 0);
        }
        for (com.taskflow.model.Task t : tareas) {
            byStatus.put(t.getStatus(), byStatus.getOrDefault(t.getStatus(), 0) + 1);
        }

        int overdue = (int) tareas.stream().filter(com.taskflow.model.Task::estaVencida).count();

        return com.taskflow.mapper.ProjectMapper.aSummary(project, byStatus, totalTasks, overdue);
    }
}
```

</details>


---

<sub>30s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\src\test\java\com\taskflow\slice\ProjectControllerSummaryTest.java.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\src\test\java\com\taskflow\slice\ProjectControllerSummaryTest.java

<details>
<summary>51 lines</summary>

```
package com.taskflow.slice;

import com.taskflow.controller.ProjectController;
import com.taskflow.dto.ProjectSummaryResponse;
import com.taskflow.model.Project;
import com.taskflow.security.JwtAuthenticationFilter;
import com.taskflow.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProjectControllerSummaryTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void getSummary_proyectoExistente_devuelve200yJSON() throws Exception {
        Project p = new Project(2L, "App MÃ³vil", "desc", 1L, LocalDate.now());
        ProjectSummaryResponse dto = new ProjectSummaryResponse(2L, "App MÃ³vil", 4, Map.of("TODO",1,"IN_PROGRESS",2,"DONE",1), 1);

        when(projectService.buscarPorId(2L)).thenReturn(Optional.of(p));
        when(projectService.resumen(p)).thenReturn(dto);

        mockMvc.perform(get("/projects/2/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(2))
                .andExpect(jsonPath("$.projectName").value("App MÃ³vil"))
                .andExpect(jsonPath("$.totalTasks").value(4))
                .andExpect(jsonPath("$.byStatus.TODO").value(1))
                .andExpect(jsonPath("$.byStatus.IN_PROGRESS").value(2))
                .andExpect(jsonPath("$.byStatus.DONE").value(1))
                .andExpect(jsonPath("$.overdue").value(1));
    }

    @Test
    void getSummary_proyectoInexistente_devuelve404() throws Exception {
        when(projectService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/projects/99/summary"))
                .andExpect(status().isNotFound());
    }
}
```

</details>


---

<sub>30s</sub>

### `view`

**view the file at C:\Users\Lenovo\taskflow-copilot-danielrousse\src\test\java\com\taskflow\unit\ProjectServiceSummaryTest.java.**

C:\Users\Lenovo\taskflow-copilot-danielrousse\src\test\java\com\taskflow\unit\ProjectServiceSummaryTest.java

<details>
<summary>60 lines</summary>

```
package com.taskflow.unit;

import com.taskflow.exception.ProjectNotFoundException;
import com.taskflow.model.Priority;
import com.taskflow.model.Project;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.repository.ProjectRepository;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import com.taskflow.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceSummaryTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ProjectService projectService;

    @Test
    void resumen_calculado_correctamente() throws Exception {
        Project p = new Project(2L, "App MÃ³vil", "desc", 1L, LocalDate.now());
        Task t1 = new Task(1L, "T01", "d", TaskStatus.TODO, Priority.MED, 2L, null, null);
                Task t2 = new Task(2L, "T02", "d", TaskStatus.DONE, Priority.MED, 2L, 1L, LocalDate.now().minusDays(5)); // pasada pero DONE -> no cuenta
                Task t3 = new Task(3L, "T03", "d", TaskStatus.IN_PROGRESS, Priority.MED, 2L, 1L, null);
                Task t4 = new Task(4L, "T04", "d", TaskStatus.IN_PROGRESS, Priority.MED, 2L, null, LocalDate.now().minusDays(1)); // vencida

        when(taskRepository.findByProjectId(2L)).thenReturn(List.of(t1, t2, t3, t4));

        var resumen = projectService.resumen(p);

        assertEquals(2L, resumen.projectId());
        assertEquals("App MÃ³vil", resumen.projectName());
        assertEquals(4, resumen.totalTasks());
        assertEquals(1, resumen.byStatus().get("TODO").intValue());
        assertEquals(2, resumen.byStatus().get("IN_PROGRESS").intValue());
        assertEquals(1, resumen.byStatus().get("DONE").intValue());
        assertEquals(1, resumen.overdue());
    }

    @Test
    void resumen_proyectoSinTareas_devuelveCeros() {
        Project p = new Project(3L, "Empty", "desc", 1L, LocalDate.now());
        when(taskRepository.findByProjectId(3L)).thenReturn(List.of());

        var resumen = projectService.resumen(p);

        assertEquals(3L, resumen.projectId());
        assertEquals(0, resumen.totalTasks());
        assertEquals(0, resumen.byStatus().get("TODO").intValue());
        assertEquals(0, resumen.byStatus().get("IN_PROGRESS").intValue());
        assertEquals(0, resumen.byStatus().get("DONE").intValue());
        assertEquals(0, resumen.overdue());
    }
}
```

</details>


---

<sub>53s</sub>

### Copilot

Resumen breve: leÃ­da la diff y la spec; los cambios implementan el endpoint y los tests solicitados. RevisiÃ³n siguiente.

| # | Gravedad | Archivo:lÃ­nea | Punto de la checklist | QuÃ© pasa | QuÃ© harÃ­as |
|---|---|---:|---|---|---|
| 1 | Sugerencia | src/main/java/com/taskflow/controller/ProjectController.java:â‰ˆ87-100 | Convenciones / Contract | El controller lanza la excepciÃ³n usando nombre totalmente cualificado (new com.taskflow.exception.ProjectNotFoundException(id)) aunque ya estÃ¡ importada. | Usar la clase importada (ProjectNotFoundException) para coherencia y legibilidad. |
| 2 | Sugerencia | src/main/java/com/taskflow/service/ProjectService.java:â‰ˆ100-140 | Reglas del dominio / Convenciones | El mÃ©todo resumen usa taskRepository.findByProjectId(projectId) directamente en vez de reutilizar ProjectService.tareasDe(projectId). | Llamar a tareasDe(projectId) para centralizar la consulta y respetar la abstracciÃ³n del servicio. |

Casos sin test:
- ninguno â€” se aÃ±adieron los dos tests pedidos: unit (ProjectServiceSummaryTest) y slice (ProjectControllerSummaryTest) que cubren proyecto con tareas y sin tareas, y 404.

Otros puntos relevantes (no bloqueantes):
- Reglas de negocio: se reutiliza Task.estaVencida() correctamente.
- DTO: ProjectSummaryResponse es record con la estructura pedida.
- byStatus siempre incluye las tres claves (mapper itera TaskStatus.values()).
- No se modificaron tests existentes.

Veredicto: APROBADO.

---

<sub>Generated by [GitHub Copilot CLI](https://github.com/features/copilot/cli)</sub>
