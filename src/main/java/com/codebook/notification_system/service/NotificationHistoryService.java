package com.codebook.notification_system.service;

import com.codebook.notification_system.dto.NotificationHistoryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface NotificationHistoryService {
    NotificationHistoryDto getNotificationHistory(Long id);
    PagedModel<NotificationHistoryDto> getAllNotificationHistories(Pageable pageable);
    NotificationHistoryDto createNotificationHistory(NotificationHistoryDto notificationHistoryDto);
    NotificationHistoryDto updateNotificationHistory(Long id, NotificationHistoryDto notificationHistoryDto);
    void deleteNotificationHistory(Long id);
}