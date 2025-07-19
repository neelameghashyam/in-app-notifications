package com.codebook.notification_system.service;

import com.codebook.notification_system.dto.NotificationUserStatusDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface NotificationUserStatusService {
    NotificationUserStatusDto getNotificationUserStatus(Long id);
    PagedModel<NotificationUserStatusDto> getAllNotificationUserStatuses(Pageable pageable);
    NotificationUserStatusDto createNotificationUserStatus(NotificationUserStatusDto notificationUserStatusDto);
    NotificationUserStatusDto updateNotificationUserStatus(Long id, NotificationUserStatusDto notificationUserStatusDto);
    void deleteNotificationUserStatus(Long id);
}