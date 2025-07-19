package com.codebook.notification_system.service;

import com.codebook.notification_system.dto.NotificationViewDto;

public interface NotificationViewService {
    NotificationViewDto getNotificationView(Long id);
    NotificationViewDto createNotificationView(NotificationViewDto notificationViewDto);
    void deleteNotificationView(Long id);
}