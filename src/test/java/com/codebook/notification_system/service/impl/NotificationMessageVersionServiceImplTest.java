package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationMessageVersionDto;
import com.codebook.notification_system.entity.NotificationMessageVersion;
import com.codebook.notification_system.mapper.NotificationMessageVersionMapper;
import com.codebook.notification_system.repository.NotificationMessageVersionRepository;
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
class NotificationMessageVersionServiceImplTest {

    @Mock
    private NotificationMessageVersionRepository notificationMessageVersionRepository;

    @Mock
    private NotificationMessageVersionMapper notificationMessageVersionMapper;

    @InjectMocks
    private NotificationMessageVersionServiceImpl notificationMessageVersionService;

    private NotificationMessageVersionDto notificationMessageVersionDto;
    private NotificationMessageVersion notificationMessageVersion;

    @BeforeEach
    void setUp() {
        notificationMessageVersionDto = new NotificationMessageVersionDto(1L, 1L, "Test Message", 1, "user", LocalDateTime.now());
        notificationMessageVersion = new NotificationMessageVersion();
        notificationMessageVersion.setVersionId(1L);
        notificationMessageVersion.setNotificationId(1L);
        notificationMessageVersion.setMessage("Test Message");
        notificationMessageVersion.setVersionNumber(1);
        notificationMessageVersion.setUpdatedBy("user");
        notificationMessageVersion.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void getNotificationMessageVersion_Success() {
        when(notificationMessageVersionRepository.findById(1L)).thenReturn(Optional.of(notificationMessageVersion));
        when(notificationMessageVersionMapper.toDto(notificationMessageVersion)).thenReturn(notificationMessageVersionDto);

        NotificationMessageVersionDto result = notificationMessageVersionService.getNotificationMessageVersion(1L);

        assertEquals(notificationMessageVersionDto, result);
        verify(notificationMessageVersionRepository).findById(1L);
        verify(notificationMessageVersionMapper).toDto(notificationMessageVersion);
    }

    @Test
    void getNotificationMessageVersion_NotFound() {
        when(notificationMessageVersionRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationMessageVersionService.getNotificationMessageVersion(1L));
        assertEquals("NotificationMessageVersion not found with id: 1", exception.getMessage());
        verify(notificationMessageVersionRepository).findById(1L);
        verifyNoInteractions(notificationMessageVersionMapper);
    }

    @Test
    void createNotificationMessageVersion_Success() {
        when(notificationMessageVersionMapper.toEntity(notificationMessageVersionDto)).thenReturn(notificationMessageVersion);
        when(notificationMessageVersionRepository.save(notificationMessageVersion)).thenReturn(notificationMessageVersion);
        when(notificationMessageVersionMapper.toDto(notificationMessageVersion)).thenReturn(notificationMessageVersionDto);

        NotificationMessageVersionDto result = notificationMessageVersionService.createNotificationMessageVersion(notificationMessageVersionDto);

        assertEquals(notificationMessageVersionDto, result);
        verify(notificationMessageVersionMapper).toEntity(notificationMessageVersionDto);
        verify(notificationMessageVersionRepository).save(notificationMessageVersion);
        verify(notificationMessageVersionMapper).toDto(notificationMessageVersion);
    }

    @Test
    void deleteNotificationMessageVersion_Success() {
        when(notificationMessageVersionRepository.findById(1L)).thenReturn(Optional.of(notificationMessageVersion));
        doNothing().when(notificationMessageVersionRepository).deleteById(1L);

        notificationMessageVersionService.deleteNotificationMessageVersion(1L);

        verify(notificationMessageVersionRepository).findById(1L);
        verify(notificationMessageVersionRepository).deleteById(1L);
    }

    @Test
    void deleteNotificationMessageVersion_NotFound() {
        when(notificationMessageVersionRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationMessageVersionService.deleteNotificationMessageVersion(1L));
        assertEquals("NotificationMessageVersion not found with id: 1", exception.getMessage());
        verify(notificationMessageVersionRepository).findById(1L);
        verifyNoMoreInteractions(notificationMessageVersionRepository);
    }
}