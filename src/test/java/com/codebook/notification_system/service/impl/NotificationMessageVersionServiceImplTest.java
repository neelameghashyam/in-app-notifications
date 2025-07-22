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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
    private Pageable pageable;

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
        pageable = PageRequest.of(0, 10);
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
    void getAllNotificationMessageVersions_Success() {
        Page<NotificationMessageVersion> page = new PageImpl<>(Collections.singletonList(notificationMessageVersion));
        when(notificationMessageVersionRepository.findAll(pageable)).thenReturn(page);
        when(notificationMessageVersionMapper.toDto(notificationMessageVersion)).thenReturn(notificationMessageVersionDto);

        PagedModel<NotificationMessageVersionDto> result = notificationMessageVersionService.getAllNotificationMessageVersions(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(notificationMessageVersionDto, result.getContent().iterator().next());
        verify(notificationMessageVersionRepository).findAll(pageable);
        verify(notificationMessageVersionMapper).toDto(notificationMessageVersion);
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
    void createNotificationMessageVersion_NullDto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationMessageVersionService.createNotificationMessageVersion(null));
        assertEquals("NotificationMessageVersionDto cannot be null", exception.getMessage());
        verifyNoInteractions(notificationMessageVersionMapper, notificationMessageVersionRepository);
    }

    @Test
    void updateNotificationMessageVersion_Success() {
        when(notificationMessageVersionRepository.findById(1L)).thenReturn(Optional.of(notificationMessageVersion));
        when(notificationMessageVersionMapper.toEntity(notificationMessageVersionDto)).thenReturn(notificationMessageVersion);
        when(notificationMessageVersionRepository.save(notificationMessageVersion)).thenReturn(notificationMessageVersion);
        when(notificationMessageVersionMapper.toDto(notificationMessageVersion)).thenReturn(notificationMessageVersionDto);

        NotificationMessageVersionDto result = notificationMessageVersionService.updateNotificationMessageVersion(1L, notificationMessageVersionDto);

        assertEquals(notificationMessageVersionDto, result);
        verify(notificationMessageVersionRepository).findById(1L);
        verify(notificationMessageVersionMapper).toEntity(notificationMessageVersionDto);
        verify(notificationMessageVersionRepository).save(notificationMessageVersion);
        verify(notificationMessageVersionMapper).toDto(notificationMessageVersion);
    }

    @Test
    void updateNotificationMessageVersion_NullDto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> notificationMessageVersionService.updateNotificationMessageVersion(1L, null));
        assertEquals("NotificationMessageVersionDto cannot be null", exception.getMessage());
        verifyNoInteractions(notificationMessageVersionMapper, notificationMessageVersionRepository);
    }

    @Test
    void updateNotificationMessageVersion_NotFound() {
        when(notificationMessageVersionRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationMessageVersionService.updateNotificationMessageVersion(1L, notificationMessageVersionDto));
        assertEquals("NotificationMessageVersion not found with id: 1", exception.getMessage());
        verify(notificationMessageVersionRepository).findById(1L);
        verifyNoInteractions(notificationMessageVersionMapper);
    }

    @Test
    void deleteNotificationMessageVersion_Success() {
        when(notificationMessageVersionRepository.findById(1L)).thenReturn(Optional.of(notificationMessageVersion));
        doNothing().when(notificationMessageVersionRepository).delete(notificationMessageVersion);

        notificationMessageVersionService.deleteNotificationMessageVersion(1L);

        verify(notificationMessageVersionRepository).findById(1L);
        verify(notificationMessageVersionRepository).delete(notificationMessageVersion);
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
