package com.codebook.notification_system.dto;

import java.time.LocalDateTime;

public record NotificationViewDto(
    Long id,
    Long notificationId,
    Long userId,
    LocalDateTime lastViewedAt
) {}