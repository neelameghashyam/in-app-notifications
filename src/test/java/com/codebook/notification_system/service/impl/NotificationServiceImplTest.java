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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    private NotificationViewRepository notificationViewRepository;

    @Mock
    private UserNotificationDismissedRepository userNotificationDismissedRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private NotificationClientMapper notificationClientMapper;

    @Mock
    private NotificationHistoryMapper notificationHistoryMapper;

    @Mock
    private NotificationMessageVersionMapper notificationMessageVersionMapper;

    @Mock
    private NotificationViewMapper notificationViewMapper;

    @Mock
    private UserNotificationDismissedMapper userNotificationDismissedMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationDto notificationDto;
    private Notification notification;
    private NotificationClientDto notificationClientDto;
    private NotificationClient notificationClient;
    private NotificationHistoryDto notificationHistoryDto;
    private NotificationHistory notificationHistory;
    private NotificationMessageVersionDto notificationMessageVersionDto;
    private NotificationMessageVersion notificationMessageVersion;
    private NotificationViewDto notificationViewDto;
    private NotificationView notificationView;
    private UserNotificationDismissedDto userNotificationDismissedDto;
    private UserNotificationDismissed userNotificationDismissed;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        notificationDto = new NotificationDto(1L, "Test Title", "Test Message", LocalDate.now(), LocalDate.now().plusDays(1),
                null, null, "ACTIVE", "ONCE", LocalDateTime.now(), LocalDateTime.now());
        notification = new Notification();
        notification.setId(1L);
        notification.setTitle("Test Title");
        notification.setMessage("Test Message");
        notification.setFromDate(LocalDate.now());
        notification.setToDate(LocalDate.now().plusDays(1));
        notification.setStatus("ACTIVE");
        notification.setFrequency("ONCE");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());

        notificationClientDto = new NotificationClientDto(1L, 1L, 1L);
        notificationClient = new NotificationClient();
        notificationClient.setId(1L);
        notificationClient.setNotificationId(1L);
        notificationClient.setClientId(1L);

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

        notificationMessageVersionDto = new NotificationMessageVersionDto(1L, 1L, "Test Message", 1, "user", LocalDateTime.now());
        notificationMessageVersion = new NotificationMessageVersion();
        notificationMessageVersion.setVersionId(1L);
        notificationMessageVersion.setNotificationId(1L);
        notificationMessageVersion.setMessage("Test Message");
        notificationMessageVersion.setVersionNumber(1);
        notificationMessageVersion.setUpdatedBy("user");
        notificationMessageVersion.setUpdatedAt(LocalDateTime.now());

        notificationViewDto = new NotificationViewDto(1L, 1L, 1L, LocalDateTime.now());
        notificationView = new NotificationView();
        notificationView.setId(1L);
        notificationView.setNotificationId(1L);
        notificationView.setUserId(1L);
        notificationView.setLastViewedAt(LocalDateTime.now());

        userNotificationDismissedDto = new UserNotificationDismissedDto(1L, 1L, 1L, LocalDateTime.now());
        userNotificationDismissed = new UserNotificationDismissed();
        userNotificationDismissed.setId(1L);
        userNotificationDismissed.setNotificationId(1L);
        userNotificationDismissed.setUserId(1L);
        userNotificationDismissed.setDismissedAt(LocalDateTime.now());

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
        Page<Notification> page = new PageImpl<>(Collections.singletonList(notification), pageable, 1);
        when(notificationRepository.findAllByOrderByTitleAsc(pageable)).thenReturn(page);
        when(notificationMapper.toDto(notification)).thenReturn(notificationDto);

        PagedModel<NotificationDto> result = notificationService.getAllNotifications(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(notificationDto, result.getContent().stream().findFirst().orElse(null));
        assertEquals(1, result.getMetadata().getTotalElements());
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
        assertNotNull(notification.getCreatedAt());
        assertNotNull(notification.getUpdatedAt());
    }

    @Test
    void updateNotification_Success() {
        Notification existingNotification = new Notification();
        existingNotification.setId(1L);
        existingNotification.setCreatedAt(LocalDateTime.now());

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(existingNotification));
        when(notificationMapper.toEntity(notificationDto)).thenReturn(notification);
        when(notificationRepository.save(notification)).thenReturn(notification);
        when(notificationMapper.toDto(notification)).thenReturn(notificationDto);

        NotificationDto result = notificationService.updateNotification(1L, notificationDto);

        assertEquals(notificationDto, result);
        verify(notificationRepository).findById(1L);
        verify(notificationMapper).toEntity(notificationDto);
        verify(notificationRepository).save(notification);
        verify(notificationMapper).toDto(notification);
        assertEquals(existingNotification.getCreatedAt(), notification.getCreatedAt());
        assertNotNull(notification.getUpdatedAt());
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
    void createNotificationClient_Success() {
        when(notificationClientMapper.toEntity(notificationClientDto)).thenReturn(notificationClient);
        when(notificationClientRepository.save(notificationClient)).thenReturn(notificationClient);
        when(notificationClientMapper.toDto(notificationClient)).thenReturn(notificationClientDto);

        NotificationClientDto result = notificationService.createNotificationClient(notificationClientDto);

        assertEquals(notificationClientDto, result);
        verify(notificationClientMapper).toEntity(notificationClientDto);
        verify(notificationClientRepository).save(notificationClient);
        verify(notificationClientMapper).toDto(notificationClient);
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
        assertNotNull(notificationHistory.getUpdatedAt());
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
        assertNotNull(notificationMessageVersion.getUpdatedAt());
    }

    @Test
    void createNotificationView_Success() {
        when(notificationViewMapper.toEntity(notificationViewDto)).thenReturn(notificationView);
        when(notificationViewRepository.save(notificationView)).thenReturn(notificationView);
        when(notificationViewMapper.toDto(notificationView)).thenReturn(notificationViewDto);

        NotificationViewDto result = notificationService.createNotificationView(notificationViewDto);

        assertEquals(notificationViewDto, result);
        verify(notificationViewMapper).toEntity(notificationViewDto);
        verify(notificationViewRepository).save(notificationView);
        verify(notificationViewMapper).toDto(notificationView);
        assertNotNull(notificationView.getLastViewedAt());
    }

    @Test
    void createUserNotificationDismissed_Success() {
        when(userNotificationDismissedMapper.toEntity(userNotificationDismissedDto)).thenReturn(userNotificationDismissed);
        when(userNotificationDismissedRepository.save(userNotificationDismissed)).thenReturn(userNotificationDismissed);
        when(userNotificationDismissedMapper.toDto(userNotificationDismissed)).thenReturn(userNotificationDismissedDto);

        UserNotificationDismissedDto result = notificationService.createUserNotificationDismissed(userNotificationDismissedDto);

        assertEquals(userNotificationDismissedDto, result);
        verify(userNotificationDismissedMapper).toEntity(userNotificationDismissedDto);
        verify(userNotificationDismissedRepository).save(userNotificationDismissed);
        verify(userNotificationDismissedMapper).toDto(userNotificationDismissed);
        assertNotNull(userNotificationDismissed.getDismissedAt());
    }
}