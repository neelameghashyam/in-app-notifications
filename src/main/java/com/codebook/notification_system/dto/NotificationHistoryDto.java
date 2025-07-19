package com.codebook.notification_system.dto;

import java.time.LocalDateTime;

public record NotificationHistoryDto(
    Long historyId,
    Long notificationId,
    String updatedBy,
    String fieldName,
    String action,
    String oldValue,
    String newValue,
    LocalDateTime updatedAt
) {}