package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationMessageVersionDto;
import com.codebook.notification_system.entity.NotificationMessageVersion;
import com.codebook.notification_system.mapper.NotificationMessageVersionMapper;
import com.codebook.notification_system.repository.NotificationMessageVersionRepository;
import com.codebook.notification_system.service.NotificationMessageVersionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationMessageVersionServiceImpl implements NotificationMessageVersionService {

    private final NotificationMessageVersionRepository notificationMessageVersionRepository;
    private final NotificationMessageVersionMapper notificationMessageVersionMapper;

    public NotificationMessageVersionServiceImpl(
            NotificationMessageVersionRepository notificationMessageVersionRepository,
            NotificationMessageVersionMapper notificationMessageVersionMapper) {
        this.notificationMessageVersionRepository = notificationMessageVersionRepository;
        this.notificationMessageVersionMapper = notificationMessageVersionMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationMessageVersionDto getNotificationMessageVersion(Long id) {
        NotificationMessageVersion notificationMessageVersion = notificationMessageVersionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationMessageVersion not found with id: " + id));
        return notificationMessageVersionMapper.toDto(notificationMessageVersion);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<NotificationMessageVersionDto> getAllNotificationMessageVersions(Pageable pageable) {
        Page<NotificationMessageVersion> versionPage = notificationMessageVersionRepository.findAll(pageable);
        List<NotificationMessageVersionDto> versionDtos = versionPage.getContent().stream()
                .map(notificationMessageVersionMapper::toDto)
                .toList();

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                versionPage.getSize(),
                versionPage.getNumber(),
                versionPage.getTotalElements(),
                versionPage.getTotalPages()
        );

        return PagedModel.of(versionDtos, metadata);
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
    public NotificationMessageVersionDto updateNotificationMessageVersion(Long id, NotificationMessageVersionDto notificationMessageVersionDto) {
        if (notificationMessageVersionDto == null) {
            throw new IllegalArgumentException("NotificationMessageVersionDto cannot be null");
        }
        NotificationMessageVersion existingNotificationMessageVersion = notificationMessageVersionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationMessageVersion not found with id: " + id));
        NotificationMessageVersion updatedNotificationMessageVersion = notificationMessageVersionMapper.toEntity(notificationMessageVersionDto);
        updatedNotificationMessageVersion.setVersionId(id);
        updatedNotificationMessageVersion.setUpdatedAt(LocalDateTime.now());
        NotificationMessageVersion savedNotificationMessageVersion = notificationMessageVersionRepository.save(updatedNotificationMessageVersion);
        return notificationMessageVersionMapper.toDto(savedNotificationMessageVersion);
    }

    @Override
    @Transactional
    public void deleteNotificationMessageVersion(Long id) {
        NotificationMessageVersion notificationMessageVersion = notificationMessageVersionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationMessageVersion not found with id: " + id));
        notificationMessageVersionRepository.delete(notificationMessageVersion);
    }
}
