package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationUserStatusDto;
import com.codebook.notification_system.entity.NotificationUserStatus;
import com.codebook.notification_system.mapper.NotificationUserStatusMapper;
import com.codebook.notification_system.repository.NotificationUserStatusRepository;
import com.codebook.notification_system.service.NotificationUserStatusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationUserStatusServiceImpl implements NotificationUserStatusService {

    private final NotificationUserStatusRepository notificationUserStatusRepository;
    private final NotificationUserStatusMapper notificationUserStatusMapper;

    public NotificationUserStatusServiceImpl(NotificationUserStatusRepository notificationUserStatusRepository,
                                            NotificationUserStatusMapper notificationUserStatusMapper) {
        this.notificationUserStatusRepository = notificationUserStatusRepository;
        this.notificationUserStatusMapper = notificationUserStatusMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationUserStatusDto getNotificationUserStatus(Long id) {
        NotificationUserStatus notificationUserStatus = notificationUserStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationUserStatus not found with id: " + id));
        return notificationUserStatusMapper.toDto(notificationUserStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<NotificationUserStatusDto> getAllNotificationUserStatuses(Pageable pageable) {
        Page<NotificationUserStatus> statusPage = notificationUserStatusRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<NotificationUserStatusDto> statusDtos = statusPage.getContent().stream()
                .map(notificationUserStatusMapper::toDto)
                .toList();

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                statusPage.getSize(),
                statusPage.getNumber(),
                statusPage.getTotalElements(),
                statusPage.getTotalPages()
        );

        return PagedModel.of(statusDtos, metadata);
    }

    @Override
    @Transactional
    public NotificationUserStatusDto createNotificationUserStatus(NotificationUserStatusDto notificationUserStatusDto) {
        NotificationUserStatus notificationUserStatus = notificationUserStatusMapper.toEntity(notificationUserStatusDto);
        notificationUserStatus.setCreatedAt(LocalDateTime.now());
        NotificationUserStatus savedNotificationUserStatus = notificationUserStatusRepository.save(notificationUserStatus);
        return notificationUserStatusMapper.toDto(savedNotificationUserStatus);
    }

    @Override
    @Transactional
    public NotificationUserStatusDto updateNotificationUserStatus(Long id, NotificationUserStatusDto notificationUserStatusDto) {
        NotificationUserStatus existingNotificationUserStatus = notificationUserStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationUserStatus not found with id: " + id));
        NotificationUserStatus updatedNotificationUserStatus = notificationUserStatusMapper.toEntity(notificationUserStatusDto);
        updatedNotificationUserStatus.setId(id);
        updatedNotificationUserStatus.setCreatedAt(existingNotificationUserStatus.getCreatedAt());
        NotificationUserStatus savedNotificationUserStatus = notificationUserStatusRepository.save(updatedNotificationUserStatus);
        return notificationUserStatusMapper.toDto(savedNotificationUserStatus);
    }

    @Override
    @Transactional
    public void deleteNotificationUserStatus(Long id) {
        NotificationUserStatus notificationUserStatus = notificationUserStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationUserStatus not found with id: " + id));
        notificationUserStatusRepository.deleteById(id);
    }
}