package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationViewDto;
import com.codebook.notification_system.entity.NotificationView;
import com.codebook.notification_system.mapper.NotificationViewMapper;
import com.codebook.notification_system.repository.NotificationViewRepository;
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
class NotificationViewServiceImplTest {

    @Mock
    private NotificationViewRepository notificationViewRepository;

    @Mock
    private NotificationViewMapper notificationViewMapper;

    @InjectMocks
    private NotificationViewServiceImpl notificationViewService;

    private NotificationViewDto notificationViewDto;
    private NotificationView notificationView;

    @BeforeEach
    void setUp() {
        notificationViewDto = new NotificationViewDto(1L, 1L, 1L, LocalDateTime.now());
        notificationView = new NotificationView();
        notificationView.setId(1L);
        notificationView.setNotificationId(1L);
        notificationView.setUserId(1L);
        notificationView.setLastViewedAt(LocalDateTime.now());
    }

    @Test
    void getNotificationView_Success() {
        when(notificationViewRepository.findById(1L)).thenReturn(Optional.of(notificationView));
        when(notificationViewMapper.toDto(notificationView)).thenReturn(notificationViewDto);

        NotificationViewDto result = notificationViewService.getNotificationView(1L);

        assertEquals(notificationViewDto, result);
        verify(notificationViewRepository).findById(1L);
        verify(notificationViewMapper).toDto(notificationView);
    }

    @Test
    void getNotificationView_NotFound() {
        when(notificationViewRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationViewService.getNotificationView(1L));
        assertEquals("NotificationView not found with id: 1", exception.getMessage());
        verify(notificationViewRepository).findById(1L);
        verifyNoInteractions(notificationViewMapper);
    }

    @Test
    void createNotificationView_Success() {
        when(notificationViewMapper.toEntity(notificationViewDto)).thenReturn(notificationView);
        when(notificationViewRepository.save(notificationView)).thenReturn(notificationView);
        when(notificationViewMapper.toDto(notificationView)).thenReturn(notificationViewDto);

        NotificationViewDto result = notificationViewService.createNotificationView(notificationViewDto);

        assertEquals(notificationViewDto, result);
        verify(notificationViewMapper).toEntity(notificationViewDto);
        verify(notificationViewRepository).save(notificationView);
        verify(notificationViewMapper).toDto(notificationView);
    }

    @Test
    void deleteNotificationView_Success() {
        when(notificationViewRepository.findById(1L)).thenReturn(Optional.of(notificationView));
        doNothing().when(notificationViewRepository).deleteById(1L);

        notificationViewService.deleteNotificationView(1L);

        verify(notificationViewRepository).findById(1L);
        verify(notificationViewRepository).deleteById(1L);
    }

    @Test
    void deleteNotificationView_NotFound() {
        when(notificationViewRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationViewService.deleteNotificationView(1L));
        assertEquals("NotificationView not found with id: 1", exception.getMessage());
        verify(notificationViewRepository).findById(1L);
        verifyNoMoreInteractions(notificationViewRepository);
    }
}