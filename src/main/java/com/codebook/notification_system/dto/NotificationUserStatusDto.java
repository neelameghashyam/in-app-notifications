package com.codebook.notification_system.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NotificationUserStatusDto(
    Long id,
    Long notificationId,
    Long userId,
    LocalDateTime seenAt,
    LocalDateTime dismissedAt,
    Boolean dontShowAgain,
    LocalDate lastShownDate,
    LocalDateTime createdAt
) {}