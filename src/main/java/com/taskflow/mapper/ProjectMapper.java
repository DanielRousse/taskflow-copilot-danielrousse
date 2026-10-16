package com.taskflow.mapper;

import com.taskflow.dto.ProjectResponse;
import com.taskflow.model.Project;

/**
 * ProjectMapper — puente DTO &lt;-&gt; dominio del lado Project. Estático, a mano, sin MapStruct.
 *
 * Mapeo trivial: la entidad guarda 'ownerId' directo (no un objeto User), así que aResponse no
 * deriva el id desde un objeto — lee p.getOwnerId() tal cual. Cuando el
 * dominio coincide con el contrato de salida (ProjectResponse con ownerId Long), el mapeo es una línea.
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
