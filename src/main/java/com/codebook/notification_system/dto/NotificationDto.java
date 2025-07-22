package com.codebook.notification_system.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NotificationDto(
    Long id,
    String title,
    String message,
    LocalDate fromDate,
    LocalDate toDate,
    LocalDateTime deletedAt,
    String deletedBy,
    String status,
    String frequency,
    Long clientId,
    Integer versionNumber,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}