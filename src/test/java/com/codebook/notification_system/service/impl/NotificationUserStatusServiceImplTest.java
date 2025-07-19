package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationUserStatusDto;
import com.codebook.notification_system.entity.NotificationUserStatus;
import com.codebook.notification_system.mapper.NotificationUserStatusMapper;
import com.codebook.notification_system.repository.NotificationUserStatusRepository;
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
class NotificationUserStatusServiceImplTest {

    @Mock
    private NotificationUserStatusRepository notificationUserStatusRepository;

    @Mock
    private NotificationUserStatusMapper notificationUserStatusMapper;

    @InjectMocks
    private NotificationUserStatusServiceImpl notificationUserStatusService;

    private NotificationUserStatus notificationUserStatus;
    private NotificationUserStatusDto notificationUserStatusDto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        notificationUserStatus = new NotificationUserStatus();
        notificationUserStatus.setId(1L);
        notificationUserStatus.setNotificationId(1L);
        notificationUserStatus.setUserId(1L);
        notificationUserStatus.setSeenAt(LocalDateTime.now());
        notificationUserStatus.setDismissedAt(null);
        notificationUserStatus.setDontShowAgain(false);
        notificationUserStatus.setLastShownDate(LocalDate.now());
        notificationUserStatus.setCreatedAt(LocalDateTime.now());

        notificationUserStatusDto = new NotificationUserStatusDto(1L, 1L, 1L, LocalDateTime.now(), null, false, LocalDate.now(), LocalDateTime.now());

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getNotificationUserStatus_Success_ReturnsNotificationUserStatusDto() {
        when(notificationUserStatusRepository.findById(anyLong())).thenReturn(Optional.of(notificationUserStatus));
        when(notificationUserStatusMapper.toDto(any(NotificationUserStatus.class))).thenReturn(notificationUserStatusDto);

        NotificationUserStatusDto result = notificationUserStatusService.getNotificationUserStatus(1L);
        assertEquals(notificationUserStatusDto, result);
        verify(notificationUserStatusRepository, times(1)).findById(1L);
        verify(notificationUserStatusMapper, times(1)).toDto(notificationUserStatus);
    }

    @Test
    void getNotificationUserStatus_NotFound_ThrowsException() {
        when(notificationUserStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationUserStatusService.getNotificationUserStatus(1L));
        assertEquals("NotificationUserStatus not found with id: 1", exception.getMessage());
        verify(notificationUserStatusRepository, times(1)).findById(1L);
        verify(notificationUserStatusMapper, never()).toDto(any());
    }

    @Test
    void getAllNotificationUserStatuses_Success_ReturnsPagedModel() {
        Page<NotificationUserStatus> page = new PageImpl<>(Collections.singletonList(notificationUserStatus));
        when(notificationUserStatusRepository.findAllByOrderByCreatedAtDesc(any(Pageable.class))).thenReturn(page);
        when(notificationUserStatusMapper.toDto(any(NotificationUserStatus.class))).thenReturn(notificationUserStatusDto);

        PagedModel<NotificationUserStatusDto> result = notificationUserStatusService.getAllNotificationUserStatuses(pageable);
        assertEquals(1, result.getContent().size());
        assertEquals(notificationUserStatusDto, result.getContent().iterator().next());
        verify(notificationUserStatusRepository, times(1)).findAllByOrderByCreatedAtDesc(pageable);
        verify(notificationUserStatusMapper, times(1)).toDto(notificationUserStatus);
    }

    @Test
    void getAllNotificationUserStatuses_EmptyPage_ReturnsEmptyPagedModel() {
        Page<NotificationUserStatus> page = new PageImpl<>(Collections.emptyList());
        when(notificationUserStatusRepository.findAllByOrderByCreatedAtDesc(any(Pageable.class))).thenReturn(page);

        PagedModel<NotificationUserStatusDto> result = notificationUserStatusService.getAllNotificationUserStatuses(pageable);
        assertTrue(result.getContent().isEmpty());
        verify(notificationUserStatusRepository, times(1)).findAllByOrderByCreatedAtDesc(pageable);
        verify(notificationUserStatusMapper, never()).toDto(any());
    }

    @Test
    void createNotificationUserStatus_Success_ReturnsNotificationUserStatusDto() {
        when(notificationUserStatusMapper.toEntity(any(NotificationUserStatusDto.class))).thenReturn(notificationUserStatus);
        when(notificationUserStatusRepository.save(any(NotificationUserStatus.class))).thenReturn(notificationUserStatus);
        when(notificationUserStatusMapper.toDto(any(NotificationUserStatus.class))).thenReturn(notificationUserStatusDto);

        NotificationUserStatusDto result = notificationUserStatusService.createNotificationUserStatus(notificationUserStatusDto);
        assertEquals(notificationUserStatusDto, result);
        verify(notificationUserStatusRepository, times(1)).save(notificationUserStatus);
        verify(notificationUserStatusMapper, times(1)).toEntity(notificationUserStatusDto);
        verify(notificationUserStatusMapper, times(1)).toDto(notificationUserStatus);
        assertNotNull(notificationUserStatus.getCreatedAt());
    }

    @Test
    void updateNotificationUserStatus_Success_ReturnsUpdatedNotificationUserStatusDto() {
        when(notificationUserStatusRepository.findById(anyLong())).thenReturn(Optional.of(notificationUserStatus));
        when(notificationUserStatusMapper.toEntity(any(NotificationUserStatusDto.class))).thenReturn(notificationUserStatus);
        when(notificationUserStatusRepository.save(any(NotificationUserStatus.class))).thenReturn(notificationUserStatus);
        when(notificationUserStatusMapper.toDto(any(NotificationUserStatus.class))).thenReturn(notificationUserStatusDto);

        NotificationUserStatusDto result = notificationUserStatusService.updateNotificationUserStatus(1L, notificationUserStatusDto);
        assertEquals(notificationUserStatusDto, result);
        verify(notificationUserStatusRepository, times(1)).findById(1L);
        verify(notificationUserStatusRepository, times(1)).save(notificationUserStatus);
        verify(notificationUserStatusMapper, times(1)).toEntity(notificationUserStatusDto);
        verify(notificationUserStatusMapper, times(1)).toDto(notificationUserStatus);
        assertNotNull(notificationUserStatus.getCreatedAt());
    }

    @Test
    void updateNotificationUserStatus_NotFound_ThrowsException() {
        when(notificationUserStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationUserStatusService.updateNotificationUserStatus(1L, notificationUserStatusDto));
        assertEquals("NotificationUserStatus not found with id: 1", exception.getMessage());
        verify(notificationUserStatusRepository, times(1)).findById(1L);
        verify(notificationUserStatusRepository, never()).save(any());
        verify(notificationUserStatusMapper, never()).toEntity(any());
        verify(notificationUserStatusMapper, never()).toDto(any());
    }

    @Test
    void deleteNotificationUserStatus_Success_DeletesNotificationUserStatus() {
        when(notificationUserStatusRepository.findById(anyLong())).thenReturn(Optional.of(notificationUserStatus));
        doNothing().when(notificationUserStatusRepository).deleteById(anyLong());

        notificationUserStatusService.deleteNotificationUserStatus(1L);
        verify(notificationUserStatusRepository, times(1)).findById(1L);
        verify(notificationUserStatusRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNotificationUserStatus_NotFound_ThrowsException() {
        when(notificationUserStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationUserStatusService.deleteNotificationUserStatus(1L));
        assertEquals("NotificationUserStatus not found with id: 1", exception.getMessage());
        verify(notificationUserStatusRepository, times(1)).findById(1L);
        verify(notificationUserStatusRepository, never()).deleteById(anyLong());
    }
}