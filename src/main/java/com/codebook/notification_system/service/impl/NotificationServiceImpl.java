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
    private final NotificationClientRepository notificationClientRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final NotificationMessageVersionRepository notificationMessageVersionRepository;
    private final NotificationViewRepository notificationViewRepository;
    private final UserNotificationDismissedRepository userNotificationDismissedRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationClientMapper notificationClientMapper;
    private final NotificationHistoryMapper notificationHistoryMapper;
    private final NotificationMessageVersionMapper notificationMessageVersionMapper;
    private final NotificationViewMapper notificationViewMapper;
    private final UserNotificationDismissedMapper userNotificationDismissedMapper;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            NotificationClientRepository notificationClientRepository,
            NotificationHistoryRepository notificationHistoryRepository,
            NotificationMessageVersionRepository notificationMessageVersionRepository,
            NotificationViewRepository notificationViewRepository,
            UserNotificationDismissedRepository userNotificationDismissedRepository,
            NotificationMapper notificationMapper,
            NotificationClientMapper notificationClientMapper,
            NotificationHistoryMapper notificationHistoryMapper,
            NotificationMessageVersionMapper notificationMessageVersionMapper,
            NotificationViewMapper notificationViewMapper,
            UserNotificationDismissedMapper userNotificationDismissedMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationClientRepository = notificationClientRepository;
        this.notificationHistoryRepository = notificationHistoryRepository;
        this.notificationMessageVersionRepository = notificationMessageVersionRepository;
        this.notificationViewRepository = notificationViewRepository;
        this.userNotificationDismissedRepository = userNotificationDismissedRepository;
        this.notificationMapper = notificationMapper;
        this.notificationClientMapper = notificationClientMapper;
        this.notificationHistoryMapper = notificationHistoryMapper;
        this.notificationMessageVersionMapper = notificationMessageVersionMapper;
        this.notificationViewMapper = notificationViewMapper;
        this.userNotificationDismissedMapper = userNotificationDismissedMapper;
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
        Notification notification = notificationMapper.toEntity(notificationDto);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        Notification savedNotification = notificationRepository.save(notification);
        return notificationMapper.toDto(savedNotification);
    }

    @Override
    @Transactional
    public NotificationDto updateNotification(Long id, NotificationDto notificationDto) {
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
    public NotificationClientDto createNotificationClient(NotificationClientDto notificationClientDto) {
        NotificationClient notificationClient = notificationClientMapper.toEntity(notificationClientDto);
        NotificationClient savedNotificationClient = notificationClientRepository.save(notificationClient);
        return notificationClientMapper.toDto(savedNotificationClient);
    }

    @Override
    @Transactional
    public NotificationHistoryDto createNotificationHistory(NotificationHistoryDto notificationHistoryDto) {
        NotificationHistory notificationHistory = notificationHistoryMapper.toEntity(notificationHistoryDto);
        notificationHistory.setUpdatedAt(LocalDateTime.now());
        NotificationHistory savedNotificationHistory = notificationHistoryRepository.save(notificationHistory);
        return notificationHistoryMapper.toDto(savedNotificationHistory);
    }

    @Override
    @Transactional
    public NotificationMessageVersionDto createNotificationMessageVersion(NotificationMessageVersionDto notificationMessageVersionDto) {
        NotificationMessageVersion notificationMessageVersion = notificationMessageVersionMapper.toEntity(notificationMessageVersionDto);
        notificationMessageVersion.setUpdatedAt(LocalDateTime.now());
        NotificationMessageVersion savedNotificationMessageVersion = notificationMessageVersionRepository.save(notificationMessageVersion);
        return notificationMessageVersionMapper.toDto(savedNotificationMessageVersion);
    }

    @Override
    @Transactional
    public NotificationViewDto createNotificationView(NotificationViewDto notificationViewDto) {
        NotificationView notificationView = notificationViewMapper.toEntity(notificationViewDto);
        notificationView.setLastViewedAt(LocalDateTime.now());
        NotificationView savedNotificationView = notificationViewRepository.save(notificationView);
        return notificationViewMapper.toDto(savedNotificationView);
    }

    @Override
    @Transactional
    public UserNotificationDismissedDto createUserNotificationDismissed(UserNotificationDismissedDto userNotificationDismissedDto) {
        UserNotificationDismissed userNotificationDismissed = userNotificationDismissedMapper.toEntity(userNotificationDismissedDto);
        userNotificationDismissed.setDismissedAt(LocalDateTime.now());
        UserNotificationDismissed savedUserNotificationDismissed = userNotificationDismissedRepository.save(userNotificationDismissed);
        return userNotificationDismissedMapper.toDto(savedUserNotificationDismissed);
    }
}