package com.codebook.notification_system.dto;

import java.time.LocalDateTime;

public record UserNotificationDismissedDto(
    Long id,
    Long notificationId,
    Long userId,
    LocalDateTime dismissedAt
) {}