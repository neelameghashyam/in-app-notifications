package com.codebook.notification_system.dto;

public record NotificationClientDto(
    Long id,
    Long notificationId,
    Long clientId
) {}