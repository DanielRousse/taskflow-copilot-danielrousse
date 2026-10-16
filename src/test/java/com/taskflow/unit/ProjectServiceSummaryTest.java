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
        Project p = new Project(2L, "App Móvil", "desc", 1L, LocalDate.now());
        Task t1 = new Task(1L, "T01", "d", TaskStatus.TODO, Priority.MED, 2L, null, null);
                Task t2 = new Task(2L, "T02", "d", TaskStatus.DONE, Priority.MED, 2L, 1L, LocalDate.now().minusDays(5)); // pasada pero DONE -> no cuenta
                Task t3 = new Task(3L, "T03", "d", TaskStatus.IN_PROGRESS, Priority.MED, 2L, 1L, null);
                Task t4 = new Task(4L, "T04", "d", TaskStatus.IN_PROGRESS, Priority.MED, 2L, null, LocalDate.now().minusDays(1)); // vencida

        when(taskRepository.findByProjectId(2L)).thenReturn(List.of(t1, t2, t3, t4));

        var resumen = projectService.resumen(p);

        assertEquals(2L, resumen.projectId());
        assertEquals("App Móvil", resumen.projectName());
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