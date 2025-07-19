package com.codebook.notification_system.service;

import com.codebook.notification_system.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface NotificationService {
    NotificationDto getNotification(Long id);
    PagedModel<NotificationDto> getAllNotifications(Pageable pageable);
    NotificationDto createNotification(NotificationDto notificationDto);
    NotificationDto updateNotification(Long id, NotificationDto notificationDto);
    void deleteNotification(Long id);

    NotificationClientDto createNotificationClient(NotificationClientDto notificationClientDto);
    NotificationHistoryDto createNotificationHistory(NotificationHistoryDto notificationHistoryDto);
    NotificationMessageVersionDto createNotificationMessageVersion(NotificationMessageVersionDto notificationMessageVersionDto);
    NotificationUserStatusDto createNotificationUserStatus(NotificationUserStatusDto notificationUserStatusDto);
}