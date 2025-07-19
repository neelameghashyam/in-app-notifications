package com.codebook.notification_system.service;

import com.codebook.notification_system.dto.NotificationClientDto;

public interface NotificationClientService {
    NotificationClientDto getNotificationClient(Long id);
    NotificationClientDto createNotificationClient(NotificationClientDto notificationClientDto);
    NotificationClientDto updateNotificationClient(Long id, NotificationClientDto notificationClientDto);
    void deleteNotificationClient(Long id);
}