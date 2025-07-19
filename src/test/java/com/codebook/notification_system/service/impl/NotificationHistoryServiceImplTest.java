package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationHistoryDto;
import com.codebook.notification_system.entity.NotificationHistory;
import com.codebook.notification_system.mapper.NotificationHistoryMapper;
import com.codebook.notification_system.repository.NotificationHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationHistoryServiceImplTest {

    @Mock
    private NotificationHistoryRepository notificationHistoryRepository;

    @Mock
    private NotificationHistoryMapper notificationHistoryMapper;

    @InjectMocks
    private NotificationHistoryServiceImpl notificationHistoryService;

    private NotificationHistoryDto notificationHistoryDto;
    private NotificationHistory notificationHistory;

    @BeforeEach
    void setUp() {
        notificationHistoryDto = new NotificationHistoryDto(1L, 1L, "user", "title", "UPDATE", "old", "new", LocalDateTime.now());
        notificationHistory = new NotificationHistory();
        notificationHistory.setHistoryId(1L);
        notificationHistory.setNotificationId(1L);
        notificationHistory.setUpdatedBy("user");
        notificationHistory.setFieldName("title");
        notificationHistory.setAction("UPDATE");
        notificationHistory.setOldValue("old");
        notificationHistory.setNewValue("new");
        notificationHistory.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void getNotificationHistory_Success() {
        when(notificationHistoryRepository.findById(1L)).thenReturn(Optional.of(notificationHistory));
        when(notificationHistoryMapper.toDto(notificationHistory)).thenReturn(notificationHistoryDto);

        NotificationHistoryDto result = notificationHistoryService.getNotificationHistory(1L);

        assertEquals(notificationHistoryDto, result);
        verify(notificationHistoryRepository).findById(1L);
        verify(notificationHistoryMapper).toDto(notificationHistory);
    }

    @Test
    void getNotificationHistory_NotFound() {
        when(notificationHistoryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationHistoryService.getNotificationHistory(1L));
        assertEquals("NotificationHistory not found with id: 1", exception.getMessage());
        verify(notificationHistoryRepository).findById(1L);
        verifyNoInteractions(notificationHistoryMapper);
    }

    @Test
    void createNotificationHistory_Success() {
        when(notificationHistoryMapper.toEntity(notificationHistoryDto)).thenReturn(notificationHistory);
        when(notificationHistoryRepository.save(notificationHistory)).thenReturn(notificationHistory);
        when(notificationHistoryMapper.toDto(notificationHistory)).thenReturn(notificationHistoryDto);

        NotificationHistoryDto result = notificationHistoryService.createNotificationHistory(notificationHistoryDto);

        assertEquals(notificationHistoryDto, result);
        verify(notificationHistoryMapper).toEntity(notificationHistoryDto);
        verify(notificationHistoryRepository).save(notificationHistory);
        verify(notificationHistoryMapper).toDto(notificationHistory);
    }

    @Test
    void deleteNotificationHistory_Success() {
        when(notificationHistoryRepository.findById(1L)).thenReturn(Optional.of(notificationHistory));
        doNothing().when(notificationHistoryRepository).deleteById(1L);

        notificationHistoryService.deleteNotificationHistory(1L);

        verify(notificationHistoryRepository).findById(1L);
        verify(notificationHistoryRepository).deleteById(1L);
    }

    @Test
    void deleteNotificationHistory_NotFound() {
        when(notificationHistoryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationHistoryService.deleteNotificationHistory(1L));
        assertEquals("NotificationHistory not found with id: 1", exception.getMessage());
        verify(notificationHistoryRepository).findById(1L);
        verifyNoMoreInteractions(notificationHistoryRepository);
    }
}