package com.codebook.notification_system.service;

import com.codebook.notification_system.dto.NotificationMessageVersionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface NotificationMessageVersionService {
    NotificationMessageVersionDto getNotificationMessageVersion(Long id);
    PagedModel<NotificationMessageVersionDto> getAllNotificationMessageVersions(Pageable pageable);
    NotificationMessageVersionDto createNotificationMessageVersion(NotificationMessageVersionDto notificationMessageVersionDto);
    NotificationMessageVersionDto updateNotificationMessageVersion(Long id, NotificationMessageVersionDto notificationMessageVersionDto);
    void deleteNotificationMessageVersion(Long id);
}