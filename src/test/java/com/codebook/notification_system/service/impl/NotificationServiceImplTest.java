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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationClientRepository notificationClientRepository;

    @Mock
    private NotificationHistoryRepository notificationHistoryRepository;

    @Mock
    private NotificationMessageVersionRepository notificationMessageVersionRepository;

    @Mock
    private NotificationUserStatusRepository notificationUserStatusRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private NotificationClientMapper notificationClientMapper;

    @Mock
    private NotificationHistoryMapper notificationHistoryMapper;

    @Mock
    private NotificationMessageVersionMapper notificationMessageVersionMapper;

    @Mock
    private NotificationUserStatusMapper notificationUserStatusMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Notification notification;
    private NotificationDto notificationDto;
    private NotificationClient notificationClient;
    private NotificationClientDto notificationClientDto;
    private NotificationHistory notificationHistory;
    private NotificationHistoryDto notificationHistoryDto;
    private NotificationMessageVersion notificationMessageVersion;
    private NotificationMessageVersionDto notificationMessageVersionDto;
    private NotificationUserStatus notificationUserStatus;
    private NotificationUserStatusDto notificationUserStatusDto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        notification.setId(1L);
        notification.setTitle("Test Title");
        notification.setMessage("Test Message");
        notification.setFromDate(LocalDate.now());
        notification.setToDate(LocalDate.now().plusDays(1));
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());
        notification.setStatus("ACTIVE");
        notification.setFrequency("DAILY");

        notificationDto = new NotificationDto(1L, "Test Title", "Test Message", LocalDate.now(), LocalDate.now().plusDays(1), null, null, "ACTIVE", "DAILY", LocalDateTime.now(), LocalDateTime.now());

        notificationClient = new NotificationClient();
        notificationClient.setId(1L);
        notificationClient.setNotificationId(1L);
        notificationClient.setClientId(1L);

        notificationClientDto = new NotificationClientDto(1L, 1L, 1L);

        notificationHistory = new NotificationHistory();
        notificationHistory.setHistoryId(1L);
        notificationHistory.setNotificationId(1L);
        notificationHistory.setUpdatedBy("user");
        notificationHistory.setFieldName("field");
        notificationHistory.setAction("UPDATE");
        notificationHistory.setOldValue("old");
        notificationHistory.setNewValue("new");
        notificationHistory.setUpdatedAt(LocalDateTime.now());

        notificationHistoryDto = new NotificationHistoryDto(1L, 1L, "user", "field", "UPDATE", "old", "new", LocalDateTime.now());

        notificationMessageVersion = new NotificationMessageVersion();
        notificationMessageVersion.setVersionId(1L);
        notificationMessageVersion.setNotificationId(1L);
        notificationMessageVersion.setMessage("Message");
        notificationMessageVersion.setVersionNumber(1);
        notificationMessageVersion.setUpdatedBy("user");
        notificationMessageVersion.setUpdatedAt(LocalDateTime.now());

        notificationMessageVersionDto = new NotificationMessageVersionDto(1L, 1L, "Message", 1, "user", LocalDateTime.now());

        notificationUserStatus = new NotificationUserStatus();
        notificationUserStatus.setId(1L);
        notificationUserStatus.setNotificationId(1L);
        notificationUserStatus.setUserId(1L);
        notificationUserStatus.setSeenAt(LocalDateTime.now());
        notificationUserStatus.setDontShowAgain(false);
        notificationUserStatus.setLastShownDate(LocalDate.now());
        notificationUserStatus.setCreatedAt(LocalDateTime.now());

        notificationUserStatusDto = new NotificationUserStatusDto(1L, 1L, 1L, LocalDateTime.now(), null, false, LocalDate.now(), LocalDateTime.now());

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getNotification_Success_ReturnsNotificationDto() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(notification));
        when(notificationMapper.toDto(any(Notification.class))).thenReturn(notificationDto);

        NotificationDto result = notificationService.getNotification(1L);
        assertEquals(notificationDto, result);
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationMapper, times(1)).toDto(notification);
    }

    @Test
    void getNotification_NotFound_ThrowsException() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationService.getNotification(1L));
        assertEquals("Notification not found with id: 1", exception.getMessage());
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationMapper, never()).toDto(any());
    }

    @Test
    void getAllNotifications_Success_ReturnsPagedModel() {
        Page<Notification> page = new PageImpl<>(Collections.singletonList(notification));
        when(notificationRepository.findAllByOrderByTitleAsc(any(Pageable.class))).thenReturn(page);
        when(notificationMapper.toDto(any(Notification.class))).thenReturn(notificationDto);

        PagedModel<NotificationDto> result = notificationService.getAllNotifications(pageable);
        assertEquals(1, result.getContent().size());
        assertEquals(notificationDto, result.getContent().iterator().next());
        verify(notificationRepository, times(1)).findAllByOrderByTitleAsc(pageable);
        verify(notificationMapper, times(1)).toDto(notification);
    }

    @Test
    void getAllNotifications_EmptyPage_ReturnsEmptyPagedModel() {
        Page<Notification> page = new PageImpl<>(Collections.emptyList());
        when(notificationRepository.findAllByOrderByTitleAsc(any(Pageable.class))).thenReturn(page);

        PagedModel<NotificationDto> result = notificationService.getAllNotifications(pageable);
        assertTrue(result.getContent().isEmpty());
        verify(notificationRepository, times(1)).findAllByOrderByTitleAsc(pageable);
        verify(notificationMapper, never()).toDto(any());
    }

    @Test
    void createNotification_Success_ReturnsNotificationDto() {
        when(notificationMapper.toEntity(any(NotificationDto.class))).thenReturn(notification);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        when(notificationMapper.toDto(any(Notification.class))).thenReturn(notificationDto);

        NotificationDto result = notificationService.createNotification(notificationDto);
        assertEquals(notificationDto, result);
        verify(notificationRepository, times(1)).save(notification);
        verify(notificationMapper, times(1)).toEntity(notificationDto);
        verify(notificationMapper, times(1)).toDto(notification);
        assertNotNull(notification.getCreatedAt());
        assertNotNull(notification.getUpdatedAt());
    }

    @Test
    void updateNotification_Success_ReturnsUpdatedNotificationDto() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(notification));
        when(notificationMapper.toEntity(any(NotificationDto.class))).thenReturn(notification);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        when(notificationMapper.toDto(any(Notification.class))).thenReturn(notificationDto);

        NotificationDto result = notificationService.updateNotification(1L, notificationDto);
        assertEquals(notificationDto, result);
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, times(1)).save(notification);
        verify(notificationMapper, times(1)).toEntity(notificationDto);
        verify(notificationMapper, times(1)).toDto(notification);
        assertNotNull(notification.getUpdatedAt());
    }

    @Test
    void updateNotification_NotFound_ThrowsException() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationService.updateNotification(1L, notificationDto));
        assertEquals("Notification not found with id: 1", exception.getMessage());
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, never()).save(any());
        verify(notificationMapper, never()).toEntity(any());
        verify(notificationMapper, never()).toDto(any());
    }

    @Test
    void deleteNotification_Success_DeletesNotification() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(notification));
        doNothing().when(notificationRepository).delete(any(Notification.class));

        notificationService.deleteNotification(1L);
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, times(1)).delete(notification);
    }

    @Test
    void deleteNotification_NotFound_ThrowsException() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationService.deleteNotification(1L));
        assertEquals("Notification not found with id: 1", exception.getMessage());
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, never()).delete(any());
    }

    @Test
    void createNotificationClient_Success_ReturnsNotificationClientDto() {
        when(notificationClientMapper.toEntity(any(NotificationClientDto.class))).thenReturn(notificationClient);
        when(notificationClientRepository.save(any(NotificationClient.class))).thenReturn(notificationClient);
        when(notificationClientMapper.toDto(any(NotificationClient.class))).thenReturn(notificationClientDto);

        NotificationClientDto result = notificationService.createNotificationClient(notificationClientDto);
        assertEquals(notificationClientDto, result);
        verify(notificationClientRepository, times(1)).save(notificationClient);
        verify(notificationClientMapper, times(1)).toEntity(notificationClientDto);
        verify(notificationClientMapper, times(1)).toDto(notificationClient);
    }

    @Test
    void createNotificationHistory_Success_ReturnsNotificationHistoryDto() {
        when(notificationHistoryMapper.toEntity(any(NotificationHistoryDto.class))).thenReturn(notificationHistory);
        when(notificationHistoryRepository.save(any(NotificationHistory.class))).thenReturn(notificationHistory);
        when(notificationHistoryMapper.toDto(any(NotificationHistory.class))).thenReturn(notificationHistoryDto);

        NotificationHistoryDto result = notificationService.createNotificationHistory(notificationHistoryDto);
        assertEquals(notificationHistoryDto, result);
        verify(notificationHistoryRepository, times(1)).save(notificationHistory);
        verify(notificationHistoryMapper, times(1)).toEntity(notificationHistoryDto);
        verify(notificationHistoryMapper, times(1)).toDto(notificationHistory);
        assertNotNull(notificationHistory.getUpdatedAt());
    }

    @Test
    void createNotificationMessageVersion_Success_ReturnsNotificationMessageVersionDto() {
        when(notificationMessageVersionMapper.toEntity(any(NotificationMessageVersionDto.class))).thenReturn(notificationMessageVersion);
        when(notificationMessageVersionRepository.save(any(NotificationMessageVersion.class))).thenReturn(notificationMessageVersion);
        when(notificationMessageVersionMapper.toDto(any(NotificationMessageVersion.class))).thenReturn(notificationMessageVersionDto);

        NotificationMessageVersionDto result = notificationService.createNotificationMessageVersion(notificationMessageVersionDto);
        assertEquals(notificationMessageVersionDto, result);
        verify(notificationMessageVersionRepository, times(1)).save(notificationMessageVersion);
        verify(notificationMessageVersionMapper, times(1)).toEntity(notificationMessageVersionDto);
        verify(notificationMessageVersionMapper, times(1)).toDto(notificationMessageVersion);
        assertNotNull(notificationMessageVersion.getUpdatedAt());
    }

    @Test
    void createNotificationUserStatus_Success_ReturnsNotificationUserStatusDto() {
        when(notificationUserStatusMapper.toEntity(any(NotificationUserStatusDto.class))).thenReturn(notificationUserStatus);
        when(notificationUserStatusRepository.save(any(NotificationUserStatus.class))).thenReturn(notificationUserStatus);
        when(notificationUserStatusMapper.toDto(any(NotificationUserStatus.class))).thenReturn(notificationUserStatusDto);

        NotificationUserStatusDto result = notificationService.createNotificationUserStatus(notificationUserStatusDto);
        assertEquals(notificationUserStatusDto, result);
        verify(notificationUserStatusRepository, times(1)).save(notificationUserStatus);
        verify(notificationUserStatusMapper, times(1)).toEntity(notificationUserStatusDto);
        verify(notificationUserStatusMapper, times(1)).toDto(notificationUserStatus);
        assertNotNull(notificationUserStatus.getCreatedAt());
    }
}