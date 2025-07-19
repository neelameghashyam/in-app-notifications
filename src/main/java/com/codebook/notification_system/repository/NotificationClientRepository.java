package com.codebook.notification_system.repository;

import com.codebook.notification_system.entity.NotificationClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationClientRepository extends JpaRepository<NotificationClient, Long> {
}