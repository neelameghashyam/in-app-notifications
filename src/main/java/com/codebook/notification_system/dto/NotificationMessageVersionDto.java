package com.codebook.notification_system.dto;

import java.time.LocalDateTime;

public record NotificationMessageVersionDto(
    Long versionId,
    Long notificationId,
    String message,
    Integer versionNumber,
    String updatedBy,
    LocalDateTime updatedAt
) {}