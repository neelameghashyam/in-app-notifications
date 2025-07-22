package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.*;
import com.codebook.notification_system.entity.*;
import com.codebook.notification_system.mapper.*;
import com.codebook.notification_system.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationHistoryRepository notificationHistoryRepository;

    @Mock
    private NotificationMessageVersionRepository notificationMessageVersionRepository;

    @Mock
    private NotificationUserStatusRepository notificationUserStatusRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private NotificationHistoryMapper notificationHistoryMapper;

    @Mock
    private NotificationMessageVersionMapper notificationMessageVersionMapper;

    @Mock
    private NotificationUserStatusMapper notificationUserStatusMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationDto notificationDto;
    private Notification notification;
    private NotificationHistoryDto notificationHistoryDto;
    private NotificationHistory notificationHistory;
    private NotificationMessageVersionDto notificationMessageVersionDto;
    private NotificationMessageVersion notificationMessageVersion;
    private NotificationUserStatusDto notificationUserStatusDto;
    private NotificationUserStatus notificationUserStatus;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        notificationDto = new NotificationDto(1L, "Test Title", "Test Message", LocalDate.now(), LocalDate.now().plusDays(1),
                null, null, "ACTIVE", "DAILY", 1L, 1, LocalDateTime.now(), LocalDateTime.now());
        notification = new Notification();
        notification.setId(1L);
        notification.setTitle("Test Title");
        notification.setMessage("Test Message");
        notification.setFromDate(LocalDate.now());
        notification.setToDate(LocalDate.now().plusDays(1));
        notification.setStatus("ACTIVE");
        notification.setFrequency("DAILY");
        notification.setClientId(1L);
        notification.setVersionNumber(1);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());

        notificationHistoryDto = new NotificationHistoryDto(1L, 1L, "user1", "title", "UPDATE", "Old Title", "New Title", LocalDateTime.now());
        notificationHistory = new NotificationHistory();
        notificationHistory.setId(1L);
        notificationHistory.setNotificationId(1L);
        notificationHistory.setUpdatedBy("user1");
        notificationHistory.setFieldName("title");
        notificationHistory.setAction("UPDATE");
        notificationHistory.setOldValue("Old Title");
        notificationHistory.setNewValue("New Title");
        notificationHistory.setUpdatedAt(LocalDateTime.now());

        notificationMessageVersionDto = new NotificationMessageVersionDto(1L, 1L, "Test Message", 1, "user", LocalDateTime.now());
        notificationMessageVersion = new NotificationMessageVersion();
        notificationMessageVersion.setVersionId(1L);
        notificationMessageVersion.setNotificationId(1L);
        notificationMessageVersion.setMessage("Test Message");
        notificationMessageVersion.setVersionNumber(1);
        notificationMessageVersion.setUpdatedBy("user");
        notificationMessageVersion.setUpdatedAt(LocalDateTime.now());

        notificationUserStatusDto = new NotificationUserStatusDto(1L, 1L, 1L, null, null, false, null, LocalDateTime.now());
        notificationUserStatus = new NotificationUserStatus();
        notificationUserStatus.setId(1L);
        notificationUserStatus.setNotificationId(1L);
        notificationUserStatus.setUserId(1L);
        notificationUserStatus.setSeenAt(null);
        notificationUserStatus.setDismissedAt(null);
        notificationUserStatus.setDontShowAgain(false);
        notificationUserStatus.setLastShownDate(null);
        notificationUserStatus.setCreatedAt(LocalDateTime.now());

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getNotification_Success() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(notificationMapper.toDto(notification)).thenReturn(notificationDto);

        NotificationDto result = notificationService.getNotification(1L);

        assertEquals(notificationDto, result);
        verify(notificationRepository).findById(1L);
        verify(notificationMapper).toDto(notification);
    }

    @Test
    void getNotification_NotFound() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationService.getNotification(1L));
        assertEquals("Notification not found with id: 1", exception.getMessage());
        verify(notificationRepository).findById(1L);
        verifyNoInteractions(notificationMapper);
    }

    @Test
    void getAllNotifications_Success() {
        Page<Notification> page = new PageImpl<>(Collections.singletonList(notification));
        when(notificationRepository.findAllByOrderByTitleAsc(pageable)).thenReturn(page);
        when(notificationMapper.toDto(notification)).thenReturn(notificationDto);

        PagedModel<NotificationDto> result = notificationService.getAllNotifications(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(notificationDto, result.getContent().iterator().next());
        verify(notificationRepository).findAllByOrderByTitleAsc(pageable);
        verify(notificationMapper).toDto(notification);
    }

    @Test
    void createNotification_Success() {
        when(notificationMapper.toEntity(notificationDto)).thenReturn(notification);
        when(notificationRepository.save(notification)).thenReturn(notification);
        when(notificationMapper.toDto(notification)).thenReturn(notificationDto);

        NotificationDto result = notificationService.createNotification(notificationDto);

        assertEquals(notificationDto, result);
        verify(notificationMapper).toEntity(notificationDto);
        verify(notificationRepository).save(notification);
        verify(notificationMapper).toDto(notification);
    }

    @Test
    void createNotification_NullDto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationService.createNotification(null));
        assertEquals("NotificationDto cannot be null", exception.getMessage());
        verifyNoInteractions(notificationMapper, notificationRepository);
    }

    @Test
    void createNotification_MissingVersionNumber() {
        NotificationDto invalidDto = new NotificationDto(1L, "Test Title", "Test Message", LocalDate.now(), LocalDate.now().plusDays(1),
                null, null, "ACTIVE", "DAILY", 1L, null, LocalDateTime.now(), LocalDateTime.now());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationService.createNotification(invalidDto));
        assertEquals("Version number is required", exception.getMessage());
        verifyNoInteractions(notificationMapper, notificationRepository);
    }

    @Test
    void updateNotification_Success() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(notificationMapper.toEntity(notificationDto)).thenReturn(notification);
        when(notificationRepository.save(notification)).thenReturn(notification);
        when(notificationMapper.toDto(notification)).thenReturn(notificationDto);

        NotificationDto result = notificationService.updateNotification(1L, notificationDto);

        assertEquals(notificationDto, result);
        verify(notificationRepository).findById(1L);
        verify(notificationMapper).toEntity(notificationDto);
        verify(notificationRepository).save(notification);
        verify(notificationMapper).toDto(notification);
    }

    @Test
    void updateNotification_NullDto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationService.updateNotification(1L, null));
        assertEquals("NotificationDto cannot be null", exception.getMessage());
        verifyNoInteractions(notificationMapper, notificationRepository);
    }

    @Test
    void updateNotification_NotFound() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationService.updateNotification(1L, notificationDto));
        assertEquals("Notification not found with id: 1", exception.getMessage());
        verify(notificationRepository).findById(1L);
        verifyNoInteractions(notificationMapper);
    }

    @Test
    void updateNotification_MissingVersionNumber() {
        NotificationDto invalidDto = new NotificationDto(1L, "Test Title", "Test Message", LocalDate.now(), LocalDate.now().plusDays(1),
                null, null, "ACTIVE", "DAILY", 1L, null, LocalDateTime.now(), LocalDateTime.now());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationService.updateNotification(1L, invalidDto));
        assertEquals("Version number is required", exception.getMessage());
        verifyNoInteractions(notificationMapper, notificationRepository);
    }

    @Test
    void deleteNotification_Success() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        doNothing().when(notificationRepository).delete(notification);

        notificationService.deleteNotification(1L);

        verify(notificationRepository).findById(1L);
        verify(notificationRepository).delete(notification);
    }

    @Test
    void deleteNotification_NotFound() {
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationService.deleteNotification(1L));
        assertEquals("Notification not found with id: 1", exception.getMessage());
        verify(notificationRepository).findById(1L);
        verifyNoMoreInteractions(notificationRepository);
    }

    @Test
    void createNotificationHistory_Success() {
        when(notificationHistoryMapper.toEntity(notificationHistoryDto)).thenReturn(notificationHistory);
        when(notificationHistoryRepository.save(notificationHistory)).thenReturn(notificationHistory);
        when(notificationHistoryMapper.toDto(notificationHistory)).thenReturn(notificationHistoryDto);

        NotificationHistoryDto result = notificationService.createNotificationHistory(notificationHistoryDto);

        assertEquals(notificationHistoryDto, result);
        verify(notificationHistoryMapper).toEntity(notificationHistoryDto);
        verify(notificationHistoryRepository).save(notificationHistory);
        verify(notificationHistoryMapper).toDto(notificationHistory);
    }

    @Test
    void createNotificationHistory_NullDto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationService.createNotificationHistory(null));
        assertEquals("NotificationHistoryDto cannot be null", exception.getMessage());
        verifyNoInteractions(notificationHistoryMapper, notificationHistoryRepository);
    }

    @Test
    void createNotificationMessageVersion_Success() {
        when(notificationMessageVersionMapper.toEntity(notificationMessageVersionDto)).thenReturn(notificationMessageVersion);
        when(notificationMessageVersionRepository.save(notificationMessageVersion)).thenReturn(notificationMessageVersion);
        when(notificationMessageVersionMapper.toDto(notificationMessageVersion)).thenReturn(notificationMessageVersionDto);

        NotificationMessageVersionDto result = notificationService.createNotificationMessageVersion(notificationMessageVersionDto);

        assertEquals(notificationMessageVersionDto, result);
        verify(notificationMessageVersionMapper).toEntity(notificationMessageVersionDto);
        verify(notificationMessageVersionRepository).save(notificationMessageVersion);
        verify(notificationMessageVersionMapper).toDto(notificationMessageVersion);
    }

    @Test
    void createNotificationMessageVersion_NullDto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationService.createNotificationMessageVersion(null));
        assertEquals("NotificationMessageVersionDto cannot be null", exception.getMessage());
        verifyNoInteractions(notificationMessageVersionMapper, notificationMessageVersionRepository);
    }

    @Test
    void createNotificationUserStatus_Success() {
        when(notificationUserStatusMapper.toEntity(notificationUserStatusDto)).thenReturn(notificationUserStatus);
        when(notificationUserStatusRepository.save(notificationUserStatus)).thenReturn(notificationUserStatus);
        when(notificationUserStatusMapper.toDto(notificationUserStatus)).thenReturn(notificationUserStatusDto);

        NotificationUserStatusDto result = notificationService.createNotificationUserStatus(notificationUserStatusDto);

        assertEquals(notificationUserStatusDto, result);
        verify(notificationUserStatusMapper).toEntity(notificationUserStatusDto);
        verify(notificationUserStatusRepository).save(notificationUserStatus);
        verify(notificationUserStatusMapper).toDto(notificationUserStatus);
    }

    @Test
    void createNotificationUserStatus_NullDto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationService.createNotificationUserStatus(null));
        assertEquals("NotificationUserStatusDto cannot be null", exception.getMessage());
        verifyNoInteractions(notificationUserStatusMapper, notificationUserStatusRepository);
    }
}
