package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationClientDto;
import com.codebook.notification_system.entity.NotificationClient;
import com.codebook.notification_system.mapper.NotificationClientMapper;
import com.codebook.notification_system.repository.NotificationClientRepository;
import com.codebook.notification_system.service.NotificationClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationClientServiceImpl implements NotificationClientService {

    private final NotificationClientRepository notificationClientRepository;
    private final NotificationClientMapper notificationClientMapper;

    public NotificationClientServiceImpl(NotificationClientRepository notificationClientRepository,
                                         NotificationClientMapper notificationClientMapper) {
        this.notificationClientRepository = notificationClientRepository;
        this.notificationClientMapper = notificationClientMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationClientDto getNotificationClient(Long id) {
        NotificationClient notificationClient = notificationClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationClient not found with id: " + id));
        return notificationClientMapper.toDto(notificationClient);
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
    public NotificationClientDto updateNotificationClient(Long id, NotificationClientDto notificationClientDto) {
        NotificationClient existingNotificationClient = notificationClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationClient not found with id: " + id));
        existingNotificationClient.setNotificationId(notificationClientDto.notificationId());
        existingNotificationClient.setClientId(notificationClientDto.clientId());
        NotificationClient savedNotificationClient = notificationClientRepository.save(existingNotificationClient);
        return notificationClientMapper.toDto(savedNotificationClient);
    }

    @Override
    @Transactional
    public void deleteNotificationClient(Long id) {
        NotificationClient notificationClient = notificationClientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationClient not found with id: " + id));
        notificationClientRepository.deleteById(id);
    }
}