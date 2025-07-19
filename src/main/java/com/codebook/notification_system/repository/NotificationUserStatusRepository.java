package com.codebook.notification_system.repository;

import com.codebook.notification_system.entity.NotificationUserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationUserStatusRepository extends JpaRepository<NotificationUserStatus, Long> {
    Page<NotificationUserStatus> findAllByOrderByCreatedAtDesc(Pageable pageable);
}