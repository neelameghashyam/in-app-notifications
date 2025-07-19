package com.codebook.notification_system.service;

import com.codebook.notification_system.dto.NotificationHistoryDto;

public interface NotificationHistoryService {
    NotificationHistoryDto getNotificationHistory(Long historyId);
    NotificationHistoryDto createNotificationHistory(NotificationHistoryDto notificationHistoryDto);
    void deleteNotificationHistory(Long historyId);
}