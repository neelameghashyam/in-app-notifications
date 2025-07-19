package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.NotificationClientDto;
import com.codebook.notification_system.entity.NotificationClient;
import com.codebook.notification_system.mapper.NotificationClientMapper;
import com.codebook.notification_system.repository.NotificationClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationClientServiceImplTest {

    @Mock
    private NotificationClientRepository notificationClientRepository;

    @Mock
    private NotificationClientMapper notificationClientMapper;

    @InjectMocks
    private NotificationClientServiceImpl notificationClientService;

    private NotificationClientDto notificationClientDto;
    private NotificationClient notificationClient;

    @BeforeEach
    void setUp() {
        notificationClientDto = new NotificationClientDto(1L, 1L, 1L);
        notificationClient = new NotificationClient();
        notificationClient.setId(1L);
        notificationClient.setNotificationId(1L);
        notificationClient.setClientId(1L);
    }

    @Test
    void getNotificationClient_Success() {
        when(notificationClientRepository.findById(1L)).thenReturn(Optional.of(notificationClient));
        when(notificationClientMapper.toDto(notificationClient)).thenReturn(notificationClientDto);

        NotificationClientDto result = notificationClientService.getNotificationClient(1L);

        assertEquals(notificationClientDto, result);
        verify(notificationClientRepository).findById(1L);
        verify(notificationClientMapper).toDto(notificationClient);
    }

    @Test
    void getNotificationClient_NotFound() {
        when(notificationClientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationClientService.getNotificationClient(1L));
        assertEquals("NotificationClient not found with id: 1", exception.getMessage());
        verify(notificationClientRepository).findById(1L);
        verifyNoInteractions(notificationClientMapper);
    }

    @Test
    void createNotificationClient_Success() {
        when(notificationClientMapper.toEntity(notificationClientDto)).thenReturn(notificationClient);
        when(notificationClientRepository.save(notificationClient)).thenReturn(notificationClient);
        when(notificationClientMapper.toDto(notificationClient)).thenReturn(notificationClientDto);

        NotificationClientDto result = notificationClientService.createNotificationClient(notificationClientDto);

        assertEquals(notificationClientDto, result);
        verify(notificationClientMapper).toEntity(notificationClientDto);
        verify(notificationClientRepository).save(notificationClient);
        verify(notificationClientMapper).toDto(notificationClient);
    }

    @Test
    void updateNotificationClient_Success() {
        NotificationClient existingClient = new NotificationClient();
        existingClient.setId(1L);
        existingClient.setNotificationId(1L);
        existingClient.setClientId(1L);

        NotificationClientDto updatedDto = new NotificationClientDto(1L, 2L, 2L);

        when(notificationClientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(notificationClientRepository.save(existingClient)).thenAnswer(invocation -> {
            NotificationClient client = invocation.getArgument(0);
            client.setNotificationId(2L);
            client.setClientId(2L);
            return client;
        });
        when(notificationClientMapper.toDto(existingClient)).thenReturn(updatedDto);

        NotificationClientDto result = notificationClientService.updateNotificationClient(1L, updatedDto);

        assertEquals(updatedDto, result);
        assertEquals(2L, existingClient.getNotificationId());
        assertEquals(2L, existingClient.getClientId());
        verify(notificationClientRepository).findById(1L);
        verify(notificationClientRepository).save(existingClient);
        verify(notificationClientMapper).toDto(existingClient);
        verifyNoMoreInteractions(notificationClientMapper);
    }

    @Test
    void updateNotificationClient_NotFound() {
        when(notificationClientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationClientService.updateNotificationClient(1L, notificationClientDto));
        assertEquals("NotificationClient not found with id: 1", exception.getMessage());
        verify(notificationClientRepository).findById(1L);
        verifyNoInteractions(notificationClientMapper);
    }

    @Test
    void deleteNotificationClient_Success() {
        when(notificationClientRepository.findById(1L)).thenReturn(Optional.of(notificationClient));
        doNothing().when(notificationClientRepository).deleteById(1L);

        notificationClientService.deleteNotificationClient(1L);

        verify(notificationClientRepository).findById(1L);
        verify(notificationClientRepository).deleteById(1L);
    }

    @Test
    void deleteNotificationClient_NotFound() {
        when(notificationClientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationClientService.deleteNotificationClient(1L));
        assertEquals("NotificationClient not found with id: 1", exception.getMessage());
        verify(notificationClientRepository).findById(1L);
        verifyNoMoreInteractions(notificationClientRepository);
    }
}