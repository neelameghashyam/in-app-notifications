package com.codebook.notification_system.service;

import com.codebook.notification_system.dto.UserNotificationDismissedDto;

public interface UserNotificationDismissedService {
    UserNotificationDismissedDto getUserNotificationDismissed(Long id);
    UserNotificationDismissedDto createUserNotificationDismissed(UserNotificationDismissedDto userNotificationDismissedDto);
    void deleteUserNotificationDismissed(Long id);
}