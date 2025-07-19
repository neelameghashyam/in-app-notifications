package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationViewDto;
import com.codebook.notification_system.entity.NotificationView;
import com.codebook.notification_system.mapper.NotificationViewMapper;
import com.codebook.notification_system.repository.NotificationViewRepository;
import com.codebook.notification_system.service.NotificationViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationViewServiceImpl implements NotificationViewService {

    private final NotificationViewRepository notificationViewRepository;
    private final NotificationViewMapper notificationViewMapper;

    public NotificationViewServiceImpl(NotificationViewRepository notificationViewRepository,
                                       NotificationViewMapper notificationViewMapper) {
        this.notificationViewRepository = notificationViewRepository;
        this.notificationViewMapper = notificationViewMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationViewDto getNotificationView(Long id) {
        NotificationView notificationView = notificationViewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationView not found with id: " + id));
        return notificationViewMapper.toDto(notificationView);
    }

    @Override
    @Transactional
    public NotificationViewDto createNotificationView(NotificationViewDto notificationViewDto) {
        NotificationView notificationView = notificationViewMapper.toEntity(notificationViewDto);
        NotificationView savedNotificationView = notificationViewRepository.save(notificationView);
        return notificationViewMapper.toDto(savedNotificationView);
    }

    @Override
    @Transactional
    public void deleteNotificationView(Long id) {
        NotificationView notificationView = notificationViewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationView not found with id: " + id));
        notificationViewRepository.deleteById(id);
    }
}