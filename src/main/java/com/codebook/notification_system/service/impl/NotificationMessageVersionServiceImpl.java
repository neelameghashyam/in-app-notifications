package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationMessageVersionDto;
import com.codebook.notification_system.entity.NotificationMessageVersion;
import com.codebook.notification_system.mapper.NotificationMessageVersionMapper;
import com.codebook.notification_system.repository.NotificationMessageVersionRepository;
import com.codebook.notification_system.service.NotificationMessageVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationMessageVersionServiceImpl implements NotificationMessageVersionService {

    private final NotificationMessageVersionRepository notificationMessageVersionRepository;
    private final NotificationMessageVersionMapper notificationMessageVersionMapper;

    public NotificationMessageVersionServiceImpl(NotificationMessageVersionRepository notificationMessageVersionRepository,
                                                NotificationMessageVersionMapper notificationMessageVersionMapper) {
        this.notificationMessageVersionRepository = notificationMessageVersionRepository;
        this.notificationMessageVersionMapper = notificationMessageVersionMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationMessageVersionDto getNotificationMessageVersion(Long versionId) {
        NotificationMessageVersion notificationMessageVersion = notificationMessageVersionRepository.findById(versionId)
                .orElseThrow(() -> new RuntimeException("NotificationMessageVersion not found with id: " + versionId));
        return notificationMessageVersionMapper.toDto(notificationMessageVersion);
    }

    @Override
    @Transactional
    public NotificationMessageVersionDto createNotificationMessageVersion(NotificationMessageVersionDto notificationMessageVersionDto) {
        NotificationMessageVersion notificationMessageVersion = notificationMessageVersionMapper.toEntity(notificationMessageVersionDto);
        NotificationMessageVersion savedNotificationMessageVersion = notificationMessageVersionRepository.save(notificationMessageVersion);
        return notificationMessageVersionMapper.toDto(savedNotificationMessageVersion);
    }

    @Override
    @Transactional
    public void deleteNotificationMessageVersion(Long versionId) {
        NotificationMessageVersion notificationMessageVersion = notificationMessageVersionRepository.findById(versionId)
                .orElseThrow(() -> new RuntimeException("NotificationMessageVersion not found with id: " + versionId));
        notificationMessageVersionRepository.deleteById(versionId);
    }
}