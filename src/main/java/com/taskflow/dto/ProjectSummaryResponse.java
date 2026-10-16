package com.taskflow.dto;

import java.util.Map;

/**
 * ProjectSummaryResponse — contrato de salida para GET /projects/{id}/summary.
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
