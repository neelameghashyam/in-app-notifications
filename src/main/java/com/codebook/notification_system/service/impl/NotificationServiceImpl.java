package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.*;
import com.codebook.notification_system.entity.*;
import com.codebook.notification_system.mapper.*;
import com.codebook.notification_system.repository.*;
import com.codebook.notification_system.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final NotificationMessageVersionRepository notificationMessageVersionRepository;
    private final NotificationUserStatusRepository notificationUserStatusRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationHistoryMapper notificationHistoryMapper;
    private final NotificationMessageVersionMapper notificationMessageVersionMapper;
    private final NotificationUserStatusMapper notificationUserStatusMapper;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            NotificationHistoryRepository notificationHistoryRepository,
            NotificationMessageVersionRepository notificationMessageVersionRepository,
            NotificationUserStatusRepository notificationUserStatusRepository,
            NotificationMapper notificationMapper,
            NotificationHistoryMapper notificationHistoryMapper,
            NotificationMessageVersionMapper notificationMessageVersionMapper,
            NotificationUserStatusMapper notificationUserStatusMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationHistoryRepository = notificationHistoryRepository;
        this.notificationMessageVersionRepository = notificationMessageVersionRepository;
        this.notificationUserStatusRepository = notificationUserStatusRepository;
        this.notificationMapper = notificationMapper;
        this.notificationHistoryMapper = notificationHistoryMapper;
        this.notificationMessageVersionMapper = notificationMessageVersionMapper;
        this.notificationUserStatusMapper = notificationUserStatusMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationDto getNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        return notificationMapper.toDto(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<NotificationDto> getAllNotifications(Pageable pageable) {
        Page<Notification> notificationPage = notificationRepository.findAllByOrderByTitleAsc(pageable);
        List<NotificationDto> notificationDtos = notificationPage.getContent().stream()
                .map(notificationMapper::toDto)
                .toList();

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                notificationPage.getSize(),
                notificationPage.getNumber(),
                notificationPage.getTotalElements(),
                notificationPage.getTotalPages()
        );

        return PagedModel.of(notificationDtos, metadata);
    }

    @Override
    @Transactional
    public NotificationDto createNotification(NotificationDto notificationDto) {
        if (notificationDto == null) {
            throw new IllegalArgumentException("NotificationDto cannot be null");
        }
        if (notificationDto.versionNumber() == null) {
            throw new IllegalArgumentException("Version number is required");
        }
        Notification notification = notificationMapper.toEntity(notificationDto);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        Notification savedNotification = notificationRepository.save(notification);
        return notificationMapper.toDto(savedNotification);
    }

    @Override
    @Transactional
    public NotificationDto updateNotification(Long id, NotificationDto notificationDto) {
        if (notificationDto == null) {
            throw new IllegalArgumentException("NotificationDto cannot be null");
        }
        if (notificationDto.versionNumber() == null) {
            throw new IllegalArgumentException("Version number is required");
        }
        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        Notification updatedNotification = notificationMapper.toEntity(notificationDto);
        updatedNotification.setId(id);
        updatedNotification.setCreatedAt(existingNotification.getCreatedAt());
        updatedNotification.setUpdatedAt(LocalDateTime.now());
        Notification savedNotification = notificationRepository.save(updatedNotification);
        return notificationMapper.toDto(savedNotification);
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        notificationRepository.delete(notification);
    }

    @Override
    @Transactional
    public NotificationHistoryDto createNotificationHistory(NotificationHistoryDto notificationHistoryDto) {
        if (notificationHistoryDto == null) {
            throw new IllegalArgumentException("NotificationHistoryDto cannot be null");
        }
        NotificationHistory notificationHistory = notificationHistoryMapper.toEntity(notificationHistoryDto);
        notificationHistory.setUpdatedAt(LocalDateTime.now());
        NotificationHistory savedNotificationHistory = notificationHistoryRepository.save(notificationHistory);
        return notificationHistoryMapper.toDto(savedNotificationHistory);
    }

    @Override
    @Transactional
    public NotificationMessageVersionDto createNotificationMessageVersion(NotificationMessageVersionDto notificationMessageVersionDto) {
        if (notificationMessageVersionDto == null) {
            throw new IllegalArgumentException("NotificationMessageVersionDto cannot be null");
        }
        NotificationMessageVersion notificationMessageVersion = notificationMessageVersionMapper.toEntity(notificationMessageVersionDto);
        notificationMessageVersion.setUpdatedAt(LocalDateTime.now());
        NotificationMessageVersion savedNotificationMessageVersion = notificationMessageVersionRepository.save(notificationMessageVersion);
        return notificationMessageVersionMapper.toDto(savedNotificationMessageVersion);
    }

    @Override
    @Transactional
    public NotificationUserStatusDto createNotificationUserStatus(NotificationUserStatusDto notificationUserStatusDto) {
        if (notificationUserStatusDto == null) {
            throw new IllegalArgumentException("NotificationUserStatusDto cannot be null");
        }
        NotificationUserStatus notificationUserStatus = notificationUserStatusMapper.toEntity(notificationUserStatusDto);
        notificationUserStatus.setCreatedAt(LocalDateTime.now());
        NotificationUserStatus savedNotificationUserStatus = notificationUserStatusRepository.save(notificationUserStatus);
        return notificationUserStatusMapper.toDto(savedNotificationUserStatus);
    }
}
