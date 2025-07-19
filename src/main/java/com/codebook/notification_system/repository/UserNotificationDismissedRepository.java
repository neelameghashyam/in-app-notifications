package com.codebook.notification_system.repository;

import com.codebook.notification_system.entity.UserNotificationDismissed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationDismissedRepository extends JpaRepository<UserNotificationDismissed, Long> {
}