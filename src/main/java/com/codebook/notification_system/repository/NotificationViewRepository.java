package com.codebook.notification_system.repository;

import com.codebook.notification_system.entity.NotificationView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationViewRepository extends JpaRepository<NotificationView, Long> {
}