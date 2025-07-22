package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationHistoryDto;
import com.codebook.notification_system.entity.NotificationHistory;
import com.codebook.notification_system.mapper.NotificationHistoryMapper;
import com.codebook.notification_system.repository.NotificationHistoryRepository;
import com.codebook.notification_system.service.NotificationHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationHistoryServiceImpl implements NotificationHistoryService {

    private final NotificationHistoryRepository notificationHistoryRepository;
    private final NotificationHistoryMapper notificationHistoryMapper;

    public NotificationHistoryServiceImpl(
            NotificationHistoryRepository notificationHistoryRepository,
            NotificationHistoryMapper notificationHistoryMapper) {
        this.notificationHistoryRepository = notificationHistoryRepository;
        this.notificationHistoryMapper = notificationHistoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationHistoryDto getNotificationHistory(Long id) {
        NotificationHistory notificationHistory = notificationHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationHistory not found with id: " + id));
        return notificationHistoryMapper.toDto(notificationHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<NotificationHistoryDto> getAllNotificationHistories(Pageable pageable) {
        Page<NotificationHistory> historyPage = notificationHistoryRepository.findAll(pageable);
        List<NotificationHistoryDto> historyDtos = historyPage.getContent().stream()
                .map(notificationHistoryMapper::toDto)
                .toList();

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(
                historyPage.getSize(),
                historyPage.getNumber(),
                historyPage.getTotalElements(),
                historyPage.getTotalPages()
        );

        return PagedModel.of(historyDtos, metadata);
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
    public NotificationHistoryDto updateNotificationHistory(Long id, NotificationHistoryDto notificationHistoryDto) {
        NotificationHistory existingNotificationHistory = notificationHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationHistory not found with id: " + id));
        NotificationHistory updatedNotificationHistory = notificationHistoryMapper.toEntity(notificationHistoryDto);
        updatedNotificationHistory.setId(id);
        updatedNotificationHistory.setUpdatedAt(LocalDateTime.now());
        NotificationHistory savedNotificationHistory = notificationHistoryRepository.save(updatedNotificationHistory);
        return notificationHistoryMapper.toDto(savedNotificationHistory);
    }

    @Override
    @Transactional
    public void deleteNotificationHistory(Long id) {
        NotificationHistory notificationHistory = notificationHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NotificationHistory not found with id: " + id));
        notificationHistoryRepository.delete(notificationHistory);
    }
}
