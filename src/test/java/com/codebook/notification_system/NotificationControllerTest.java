package com.codebook.notification_system;

import com.codebook.notification_system.controller.NotificationController;
import com.codebook.notification_system.dto.*;
import com.codebook.notification_system.service.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private NotificationClientService notificationClientService;

    @Mock
    private NotificationHistoryService notificationHistoryService;

    @Mock
    private NotificationMessageVersionService notificationMessageVersionService;

    @Mock
    private NotificationViewService notificationViewService;

    @Mock
    private UserNotificationDismissedService userNotificationDismissedService;

    @InjectMocks
    private NotificationController notificationController;

    private NotificationDto notificationDto;
    private NotificationClientDto notificationClientDto;
    private NotificationHistoryDto notificationHistoryDto;
    private NotificationMessageVersionDto notificationMessageVersionDto;
    private NotificationViewDto notificationViewDto;
    private UserNotificationDismissedDto userNotificationDismissedDto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        notificationDto = new NotificationDto(1L, "Test Title", "Test Message", LocalDate.now(), LocalDate.now().plusDays(1),
                null, null, "ACTIVE", "ONCE", LocalDateTime.now(), LocalDateTime.now());
        notificationClientDto = new NotificationClientDto(1L, 1L, 1L);
        notificationHistoryDto = new NotificationHistoryDto(1L, 1L, "user", "title", "UPDATE", "old", "new", LocalDateTime.now());
        notificationMessageVersionDto = new NotificationMessageVersionDto(1L, 1L, "Test Message", 1, "user", LocalDateTime.now());
        notificationViewDto = new NotificationViewDto(1L, 1L, 1L, LocalDateTime.now());
        userNotificationDismissedDto = new UserNotificationDismissedDto(1L, 1L, 1L, LocalDateTime.now());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getNotification_Success() {
        when(notificationService.getNotification(1L)).thenReturn(notificationDto);

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.getNotification(1L);

        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationDto, response.join().getBody());
        verify(notificationService).getNotification(1L);
    }

    @Test
    void getNotification_NotFound() {
        when(notificationService.getNotification(1L)).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.getNotification(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService).getNotification(1L);
    }

    @Test
    void getAllNotifications_Success() {
        Page<NotificationDto> page = new PageImpl<>(Collections.singletonList(notificationDto), pageable, 1);
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<NotificationDto> pagedModel = PagedModel.of(page.getContent(), metadata);

        when(notificationService.getAllNotifications(pageable)).thenReturn(pagedModel);

        CompletableFuture<ResponseEntity<PagedModel<NotificationDto>>> response = notificationController.getAllNotifications(pageable);

        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(pagedModel, response.join().getBody());
        verify(notificationService).getAllNotifications(pageable);
    }

    @Test
    void getAllNotifications_Failure() {
        when(notificationService.getAllNotifications(pageable)).thenThrow(new RuntimeException("Error"));

        CompletableFuture<ResponseEntity<PagedModel<NotificationDto>>> response = notificationController.getAllNotifications(pageable);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService).getAllNotifications(pageable);
    }

    @Test
    void createNotification_Success() {
        when(notificationService.createNotification(any(NotificationDto.class))).thenReturn(notificationDto);

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.createNotification(notificationDto);

        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(notificationDto, response.join().getBody());
        verify(notificationService).createNotification(any(NotificationDto.class));
    }

    @Test
    void createNotification_BadRequest() {
        when(notificationService.createNotification(any(NotificationDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.createNotification(notificationDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService).createNotification(any(NotificationDto.class));
    }

    @Test
    void updateNotification_Success() {
        when(notificationService.updateNotification(eq(1L), any(NotificationDto.class))).thenReturn(notificationDto);

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.updateNotification(1L, notificationDto);

        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationDto, response.join().getBody());
        verify(notificationService).updateNotification(eq(1L), any(NotificationDto.class));
    }

    @Test
    void updateNotification_NotFound() {
        when(notificationService.updateNotification(eq(1L), any(NotificationDto.class))).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.updateNotification(1L, notificationDto);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService).updateNotification(eq(1L), any(NotificationDto.class));
    }

    @Test
    void deleteNotification_Success() {
        doNothing().when(notificationService).deleteNotification(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotification(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(notificationService).deleteNotification(1L);
    }

    @Test
    void deleteNotification_NotFound() {
        doThrow(new RuntimeException("Not found")).when(notificationService).deleteNotification(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotification(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(notificationService).deleteNotification(1L);
    }

    @Test
    void getNotificationClient_Success() {
        when(notificationClientService.getNotificationClient(1L)).thenReturn(notificationClientDto);

        CompletableFuture<ResponseEntity<NotificationClientDto>> response = notificationController.getNotificationClient(1L);

        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationClientDto, response.join().getBody());
        verify(notificationClientService).getNotificationClient(1L);
    }

    @Test
    void getNotificationClient_NotFound() {
        when(notificationClientService.getNotificationClient(1L)).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationClientDto>> response = notificationController.getNotificationClient(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationClientService).getNotificationClient(1L);
    }

    @Test
    void createNotificationClient_Success() {
        when(notificationClientService.createNotificationClient(any(NotificationClientDto.class))).thenReturn(notificationClientDto);

        CompletableFuture<ResponseEntity<NotificationClientDto>> response = notificationController.createNotificationClient(notificationClientDto);

        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(notificationClientDto, response.join().getBody());
        verify(notificationClientService).createNotificationClient(any(NotificationClientDto.class));
    }

    @Test
    void createNotificationClient_BadRequest() {
        when(notificationClientService.createNotificationClient(any(NotificationClientDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<NotificationClientDto>> response = notificationController.createNotificationClient(notificationClientDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationClientService).createNotificationClient(any(NotificationClientDto.class));
    }

    @Test
    void updateNotificationClient_Success() {
        when(notificationClientService.updateNotificationClient(eq(1L), any(NotificationClientDto.class))).thenReturn(notificationClientDto);

        CompletableFuture<ResponseEntity<NotificationClientDto>> response = notificationController.updateNotificationClient(1L, notificationClientDto);

        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationClientDto, response.join().getBody());
        verify(notificationClientService).updateNotificationClient(eq(1L), any(NotificationClientDto.class));
    }

    @Test
    void updateNotificationClient_NotFound() {
        when(notificationClientService.updateNotificationClient(eq(1L), any(NotificationClientDto.class))).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationClientDto>> response = notificationController.updateNotificationClient(1L, notificationClientDto);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationClientService).updateNotificationClient(eq(1L), any(NotificationClientDto.class));
    }

    @Test
    void deleteNotificationClient_Success() {
        doNothing().when(notificationClientService).deleteNotificationClient(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationClient(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(notificationClientService).deleteNotificationClient(1L);
    }

    @Test
    void deleteNotificationClient_NotFound() {
        doThrow(new RuntimeException("Not found")).when(notificationClientService).deleteNotificationClient(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationClient(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(notificationClientService).deleteNotificationClient(1L);
    }

    @Test
    void getNotificationHistory_Success() {
        when(notificationHistoryService.getNotificationHistory(1L)).thenReturn(notificationHistoryDto);

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.getNotificationHistory(1L);

        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationHistoryDto, response.join().getBody());
        verify(notificationHistoryService).getNotificationHistory(1L);
    }

    @Test
    void getNotificationHistory_NotFound() {
        when(notificationHistoryService.getNotificationHistory(1L)).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.getNotificationHistory(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationHistoryService).getNotificationHistory(1L);
    }

    @Test
    void createNotificationHistory_Success() {
        when(notificationHistoryService.createNotificationHistory(any(NotificationHistoryDto.class))).thenReturn(notificationHistoryDto);

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.createNotificationHistory(notificationHistoryDto);

        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(notificationHistoryDto, response.join().getBody());
        verify(notificationHistoryService).createNotificationHistory(any(NotificationHistoryDto.class));
    }

    @Test
    void createNotificationHistory_BadRequest() {
        when(notificationHistoryService.createNotificationHistory(any(NotificationHistoryDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.createNotificationHistory(notificationHistoryDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationHistoryService).createNotificationHistory(any(NotificationHistoryDto.class));
    }

    @Test
    void deleteNotificationHistory_Success() {
        doNothing().when(notificationHistoryService).deleteNotificationHistory(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationHistory(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(notificationHistoryService).deleteNotificationHistory(1L);
    }

    @Test
    void deleteNotificationHistory_NotFound() {
        doThrow(new RuntimeException("Not found")).when(notificationHistoryService).deleteNotificationHistory(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationHistory(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(notificationHistoryService).deleteNotificationHistory(1L);
    }

    @Test
    void getNotificationMessageVersion_Success() {
        when(notificationMessageVersionService.getNotificationMessageVersion(1L)).thenReturn(notificationMessageVersionDto);

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.getNotificationMessageVersion(1L);

        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationMessageVersionDto, response.join().getBody());
        verify(notificationMessageVersionService).getNotificationMessageVersion(1L);
    }

    @Test
    void getNotificationMessageVersion_NotFound() {
        when(notificationMessageVersionService.getNotificationMessageVersion(1L)).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.getNotificationMessageVersion(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationMessageVersionService).getNotificationMessageVersion(1L);
    }

    @Test
    void createNotificationMessageVersion_Success() {
        when(notificationMessageVersionService.createNotificationMessageVersion(any(NotificationMessageVersionDto.class))).thenReturn(notificationMessageVersionDto);

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.createNotificationMessageVersion(notificationMessageVersionDto);

        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(notificationMessageVersionDto, response.join().getBody());
        verify(notificationMessageVersionService).createNotificationMessageVersion(any(NotificationMessageVersionDto.class));
    }

    @Test
    void createNotificationMessageVersion_BadRequest() {
        when(notificationMessageVersionService.createNotificationMessageVersion(any(NotificationMessageVersionDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.createNotificationMessageVersion(notificationMessageVersionDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationMessageVersionService).createNotificationMessageVersion(any(NotificationMessageVersionDto.class));
    }

    @Test
    void deleteNotificationMessageVersion_Success() {
        doNothing().when(notificationMessageVersionService).deleteNotificationMessageVersion(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationMessageVersion(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(notificationMessageVersionService).deleteNotificationMessageVersion(1L);
    }

    @Test
    void deleteNotificationMessageVersion_NotFound() {
        doThrow(new RuntimeException("Not found")).when(notificationMessageVersionService).deleteNotificationMessageVersion(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationMessageVersion(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(notificationMessageVersionService).deleteNotificationMessageVersion(1L);
    }

    @Test
    void getNotificationView_Success() {
        when(notificationViewService.getNotificationView(1L)).thenReturn(notificationViewDto);

        CompletableFuture<ResponseEntity<NotificationViewDto>> response = notificationController.getNotificationView(1L);

        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationViewDto, response.join().getBody());
        verify(notificationViewService).getNotificationView(1L);
    }

    @Test
    void getNotificationView_NotFound() {
        when(notificationViewService.getNotificationView(1L)).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationViewDto>> response = notificationController.getNotificationView(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationViewService).getNotificationView(1L);
    }

    @Test
    void createNotificationView_Success() {
        when(notificationViewService.createNotificationView(any(NotificationViewDto.class))).thenReturn(notificationViewDto);

        CompletableFuture<ResponseEntity<NotificationViewDto>> response = notificationController.createNotificationView(notificationViewDto);

        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(notificationViewDto, response.join().getBody());
        verify(notificationViewService).createNotificationView(any(NotificationViewDto.class));
    }

    @Test
    void createNotificationView_BadRequest() {
        when(notificationViewService.createNotificationView(any(NotificationViewDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<NotificationViewDto>> response = notificationController.createNotificationView(notificationViewDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationViewService).createNotificationView(any(NotificationViewDto.class));
    }

    @Test
    void deleteNotificationView_Success() {
        doNothing().when(notificationViewService).deleteNotificationView(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationView(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(notificationViewService).deleteNotificationView(1L);
    }

    @Test
    void deleteNotificationView_NotFound() {
        doThrow(new RuntimeException("Not found")).when(notificationViewService).deleteNotificationView(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationView(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(notificationViewService).deleteNotificationView(1L);
    }

    @Test
    void getUserNotificationDismissed_Success() {
        when(userNotificationDismissedService.getUserNotificationDismissed(1L)).thenReturn(userNotificationDismissedDto);

        CompletableFuture<ResponseEntity<UserNotificationDismissedDto>> response = notificationController.getUserNotificationDismissed(1L);

        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(userNotificationDismissedDto, response.join().getBody());
        verify(userNotificationDismissedService).getUserNotificationDismissed(1L);
    }

    @Test
    void getUserNotificationDismissed_NotFound() {
        when(userNotificationDismissedService.getUserNotificationDismissed(1L)).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<UserNotificationDismissedDto>> response = notificationController.getUserNotificationDismissed(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(userNotificationDismissedService).getUserNotificationDismissed(1L);
    }

    @Test
    void createUserNotificationDismissed_Success() {
        when(userNotificationDismissedService.createUserNotificationDismissed(any(UserNotificationDismissedDto.class))).thenReturn(userNotificationDismissedDto);

        CompletableFuture<ResponseEntity<UserNotificationDismissedDto>> response = notificationController.createUserNotificationDismissed(userNotificationDismissedDto);

        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(userNotificationDismissedDto, response.join().getBody());
        verify(userNotificationDismissedService).createUserNotificationDismissed(any(UserNotificationDismissedDto.class));
    }

    @Test
    void createUserNotificationDismissed_BadRequest() {
        when(userNotificationDismissedService.createUserNotificationDismissed(any(UserNotificationDismissedDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<UserNotificationDismissedDto>> response = notificationController.createUserNotificationDismissed(userNotificationDismissedDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(userNotificationDismissedService).createUserNotificationDismissed(any(UserNotificationDismissedDto.class));
    }

    @Test
    void deleteUserNotificationDismissed_Success() {
        doNothing().when(userNotificationDismissedService).deleteUserNotificationDismissed(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteUserNotificationDismissed(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(userNotificationDismissedService).deleteUserNotificationDismissed(1L);
    }

    @Test
    void deleteUserNotificationDismissed_NotFound() {
        doThrow(new RuntimeException("Not found")).when(userNotificationDismissedService).deleteUserNotificationDismissed(1L);

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteUserNotificationDismissed(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(userNotificationDismissedService).deleteUserNotificationDismissed(1L);
    }
}