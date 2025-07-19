package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationHistoryDto;
import com.codebook.notification_system.entity.NotificationHistory;
import com.codebook.notification_system.mapper.NotificationHistoryMapper;
import com.codebook.notification_system.repository.NotificationHistoryRepository;
import com.codebook.notification_system.service.NotificationHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationHistoryServiceImpl implements NotificationHistoryService {

    private final NotificationHistoryRepository notificationHistoryRepository;
    private final NotificationHistoryMapper notificationHistoryMapper;

    public NotificationHistoryServiceImpl(NotificationHistoryRepository notificationHistoryRepository,
                                          NotificationHistoryMapper notificationHistoryMapper) {
        this.notificationHistoryRepository = notificationHistoryRepository;
        this.notificationHistoryMapper = notificationHistoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationHistoryDto getNotificationHistory(Long historyId) {
        NotificationHistory notificationHistory = notificationHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("NotificationHistory not found with id: " + historyId));
        return notificationHistoryMapper.toDto(notificationHistory);
    }

    @Override
    @Transactional
    public NotificationHistoryDto createNotificationHistory(NotificationHistoryDto notificationHistoryDto) {
        NotificationHistory notificationHistory = notificationHistoryMapper.toEntity(notificationHistoryDto);
        NotificationHistory savedNotificationHistory = notificationHistoryRepository.save(notificationHistory);
        return notificationHistoryMapper.toDto(savedNotificationHistory);
    }

    @Override
    @Transactional
    public void deleteNotificationHistory(Long historyId) {
        NotificationHistory notificationHistory = notificationHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("NotificationHistory not found with id: " + historyId));
        notificationHistoryRepository.deleteById(historyId);
    }
}