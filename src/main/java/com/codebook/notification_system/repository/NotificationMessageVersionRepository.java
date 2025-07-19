package com.codebook.notification_system.repository;

import com.codebook.notification_system.entity.NotificationMessageVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationMessageVersionRepository extends JpaRepository<NotificationMessageVersion, Long> {
}