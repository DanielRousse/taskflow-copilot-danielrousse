package com.taskflow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * TaskAssigneeUpdateRequest — DTO de entrada para PATCH /tasks/{id}/assignee.
 * Valida que assigneeId exista y sea positivo.
 */
public record TaskAssigneeUpdateRequest(
        @NotNull
        @Positive
        Long assigneeId
) {
}
