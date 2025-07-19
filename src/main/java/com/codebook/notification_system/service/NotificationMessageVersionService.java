package com.codebook.notification_system.service;

import com.codebook.notification_system.dto.NotificationMessageVersionDto;

public interface NotificationMessageVersionService {
    NotificationMessageVersionDto getNotificationMessageVersion(Long versionId);
    NotificationMessageVersionDto createNotificationMessageVersion(NotificationMessageVersionDto notificationMessageVersionDto);
    void deleteNotificationMessageVersion(Long versionId);
}