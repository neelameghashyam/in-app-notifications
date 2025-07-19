package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.UserNotificationDismissedDto;
import com.codebook.notification_system.entity.UserNotificationDismissed;
import com.codebook.notification_system.mapper.UserNotificationDismissedMapper;
import com.codebook.notification_system.repository.UserNotificationDismissedRepository;
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
class UserNotificationDismissedServiceImplTest {

    @Mock
    private UserNotificationDismissedRepository userNotificationDismissedRepository;

    @Mock
    private UserNotificationDismissedMapper userNotificationDismissedMapper;

    @InjectMocks
    private UserNotificationDismissedServiceImpl userNotificationDismissedService;

    private UserNotificationDismissedDto userNotificationDismissedDto;
    private UserNotificationDismissed userNotificationDismissed;

    @BeforeEach
    void setUp() {
        userNotificationDismissedDto = new UserNotificationDismissedDto(1L, 1L, 1L, LocalDateTime.now());
        userNotificationDismissed = new UserNotificationDismissed();
        userNotificationDismissed.setId(1L);
        userNotificationDismissed.setNotificationId(1L);
        userNotificationDismissed.setUserId(1L);
        userNotificationDismissed.setDismissedAt(LocalDateTime.now());
    }

    @Test
    void getUserNotificationDismissed_Success() {
        when(userNotificationDismissedRepository.findById(1L)).thenReturn(Optional.of(userNotificationDismissed));
        when(userNotificationDismissedMapper.toDto(userNotificationDismissed)).thenReturn(userNotificationDismissedDto);

        UserNotificationDismissedDto result = userNotificationDismissedService.getUserNotificationDismissed(1L);

        assertEquals(userNotificationDismissedDto, result);
        verify(userNotificationDismissedRepository).findById(1L);
        verify(userNotificationDismissedMapper).toDto(userNotificationDismissed);
    }

    @Test
    void getUserNotificationDismissed_NotFound() {
        when(userNotificationDismissedRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userNotificationDismissedService.getUserNotificationDismissed(1L));
        assertEquals("UserNotificationDismissed not found with id: 1", exception.getMessage());
        verify(userNotificationDismissedRepository).findById(1L);
        verifyNoInteractions(userNotificationDismissedMapper);
    }

    @Test
    void createUserNotificationDismissed_Success() {
        when(userNotificationDismissedMapper.toEntity(userNotificationDismissedDto)).thenReturn(userNotificationDismissed);
        when(userNotificationDismissedRepository.save(userNotificationDismissed)).thenReturn(userNotificationDismissed);
        when(userNotificationDismissedMapper.toDto(userNotificationDismissed)).thenReturn(userNotificationDismissedDto);

        UserNotificationDismissedDto result = userNotificationDismissedService.createUserNotificationDismissed(userNotificationDismissedDto);

        assertEquals(userNotificationDismissedDto, result);
        verify(userNotificationDismissedMapper).toEntity(userNotificationDismissedDto);
        verify(userNotificationDismissedRepository).save(userNotificationDismissed);
        verify(userNotificationDismissedMapper).toDto(userNotificationDismissed);
    }

    @Test
    void deleteUserNotificationDismissed_Success() {
        when(userNotificationDismissedRepository.findById(1L)).thenReturn(Optional.of(userNotificationDismissed));
        doNothing().when(userNotificationDismissedRepository).deleteById(1L);

        userNotificationDismissedService.deleteUserNotificationDismissed(1L);

        verify(userNotificationDismissedRepository).findById(1L);
        verify(userNotificationDismissedRepository).deleteById(1L);
    }

    @Test
    void deleteUserNotificationDismissed_NotFound() {
        when(userNotificationDismissedRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userNotificationDismissedService.deleteUserNotificationDismissed(1L));
        assertEquals("UserNotificationDismissed not found with id: 1", exception.getMessage());
        verify(userNotificationDismissedRepository).findById(1L);
        verifyNoMoreInteractions(userNotificationDismissedRepository);
    }
}