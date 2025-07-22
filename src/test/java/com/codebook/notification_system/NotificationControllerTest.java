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
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private NotificationHistoryService notificationHistoryService;

    @Mock
    private NotificationMessageVersionService notificationMessageVersionService;

    @Mock
    private NotificationUserStatusService notificationUserStatusService;

    @InjectMocks
    private NotificationController notificationController;

    private NotificationDto notificationDto;
    private NotificationHistoryDto notificationHistoryDto;
    private NotificationMessageVersionDto notificationMessageVersionDto;
    private NotificationUserStatusDto notificationUserStatusDto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        notificationDto = new NotificationDto(1L, "Test Title", "Test Message", LocalDate.now(), LocalDate.now().plusDays(1),
                null, null, "ACTIVE", "DAILY", 1L, 1, LocalDateTime.now(), LocalDateTime.now());
        notificationHistoryDto = new NotificationHistoryDto(1L, 1L, "user", "field", "UPDATE", "old", "new", LocalDateTime.now());
        notificationMessageVersionDto = new NotificationMessageVersionDto(1L, 1L, "Message", 1, "user", LocalDateTime.now());
        notificationUserStatusDto = new NotificationUserStatusDto(1L, 1L, 1L, LocalDateTime.now(), null, false, LocalDate.now(), LocalDateTime.now());
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getNotification_Success_ReturnsNotificationDto() {
        when(notificationService.getNotification(anyLong())).thenReturn(notificationDto);

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.getNotification(1L);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationDto, response.join().getBody());
        verify(notificationService, times(1)).getNotification(1L);
    }

    @Test
    void getNotification_NotFound_ReturnsNotFound() {
        when(notificationService.getNotification(anyLong())).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.getNotification(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService, times(1)).getNotification(1L);
    }

    @Test
    void getAllNotifications_Success_ReturnsPagedModel() {
        Page<NotificationDto> page = new PageImpl<>(Collections.singletonList(notificationDto));
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<NotificationDto> pagedModel = PagedModel.of(page.getContent(), metadata);
        when(notificationService.getAllNotifications(any(Pageable.class))).thenReturn(pagedModel);

        CompletableFuture<ResponseEntity<PagedModel<NotificationDto>>> response = notificationController.getAllNotifications(pageable);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(pagedModel, response.join().getBody());
        verify(notificationService, times(1)).getAllNotifications(pageable);
    }

    @Test
    void getAllNotifications_Failure_ReturnsInternalServerError() {
        when(notificationService.getAllNotifications(any(Pageable.class))).thenThrow(new RuntimeException("Error"));

        CompletableFuture<ResponseEntity<PagedModel<NotificationDto>>> response = notificationController.getAllNotifications(pageable);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService, times(1)).getAllNotifications(pageable);
    }

    @Test
    void createNotification_Success_ReturnsCreated() {
        when(notificationService.createNotification(any(NotificationDto.class))).thenReturn(notificationDto);

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.createNotification(notificationDto);
        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(notificationDto, response.join().getBody());
        verify(notificationService, times(1)).createNotification(notificationDto);
    }

    @Test
    void createNotification_NullDto_ReturnsBadRequest() {
        when(notificationService.createNotification(isNull())).thenThrow(new IllegalArgumentException("NotificationDto cannot be null"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.createNotification(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService, times(1)).createNotification(null);
    }

    @Test
    void createNotification_MissingVersionNumber_ReturnsBadRequest() {
        NotificationDto invalidDto = new NotificationDto(1L, "Test Title", "Test Message", LocalDate.now(), LocalDate.now().plusDays(1),
                null, null, "ACTIVE", "DAILY", 1L, null, LocalDateTime.now(), LocalDateTime.now());
        when(notificationService.createNotification(any(NotificationDto.class))).thenThrow(new IllegalArgumentException("Version number is required"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.createNotification(invalidDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService, times(1)).createNotification(invalidDto);
    }

    @Test
    void createNotification_Failure_ReturnsBadRequest() {
        when(notificationService.createNotification(any(NotificationDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.createNotification(notificationDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService, times(1)).createNotification(notificationDto);
    }

    @Test
    void updateNotification_Success_ReturnsUpdatedNotification() {
        when(notificationService.updateNotification(anyLong(), any(NotificationDto.class))).thenReturn(notificationDto);

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.updateNotification(1L, notificationDto);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationDto, response.join().getBody());
        verify(notificationService, times(1)).updateNotification(1L, notificationDto);
    }

    @Test
    void updateNotification_NullDto_ReturnsBadRequest() {
        when(notificationService.updateNotification(anyLong(), isNull())).thenThrow(new IllegalArgumentException("NotificationDto cannot be null"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.updateNotification(1L, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService, times(1)).updateNotification(1L, null);
    }

    @Test
    void updateNotification_MissingVersionNumber_ReturnsBadRequest() {
        NotificationDto invalidDto = new NotificationDto(1L, "Test Title", "Test Message", LocalDate.now(), LocalDate.now().plusDays(1),
                null, null, "ACTIVE", "DAILY", 1L, null, LocalDateTime.now(), LocalDateTime.now());
        when(notificationService.updateNotification(anyLong(), any(NotificationDto.class))).thenThrow(new IllegalArgumentException("Version number is required"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.updateNotification(1L, invalidDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService, times(1)).updateNotification(1L, invalidDto);
    }

    @Test
    void updateNotification_NotFound_ReturnsNotFound() {
        when(notificationService.updateNotification(anyLong(), any(NotificationDto.class))).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationDto>> response = notificationController.updateNotification(1L, notificationDto);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationService, times(1)).updateNotification(1L, notificationDto);
    }

    @Test
    void deleteNotification_Success_ReturnsNoContent() {
        doNothing().when(notificationService).deleteNotification(anyLong());

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotification(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(notificationService, times(1)).deleteNotification(1L);
    }

    @Test
    void deleteNotification_NotFound_ReturnsNotFound() {
        doThrow(new RuntimeException("Not found")).when(notificationService).deleteNotification(anyLong());

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotification(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(notificationService, times(1)).deleteNotification(1L);
    }

    @Test
    void getNotificationHistory_Success_ReturnsNotificationHistoryDto() {
        when(notificationHistoryService.getNotificationHistory(anyLong())).thenReturn(notificationHistoryDto);

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.getNotificationHistory(1L);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationHistoryDto, response.join().getBody());
        verify(notificationHistoryService, times(1)).getNotificationHistory(1L);
    }

    @Test
    void getNotificationHistory_NotFound_ReturnsNotFound() {
        when(notificationHistoryService.getNotificationHistory(anyLong())).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.getNotificationHistory(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationHistoryService, times(1)).getNotificationHistory(1L);
    }

    @Test
    void getAllNotificationHistories_Success_ReturnsPagedModel() {
        Page<NotificationHistoryDto> page = new PageImpl<>(Collections.singletonList(notificationHistoryDto));
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<NotificationHistoryDto> pagedModel = PagedModel.of(page.getContent(), metadata);
        when(notificationHistoryService.getAllNotificationHistories(any(Pageable.class))).thenReturn(pagedModel);

        CompletableFuture<ResponseEntity<PagedModel<NotificationHistoryDto>>> response = notificationController.getAllNotificationHistories(pageable);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(pagedModel, response.join().getBody());
        verify(notificationHistoryService, times(1)).getAllNotificationHistories(pageable);
    }

    @Test
    void getAllNotificationHistories_Failure_ReturnsInternalServerError() {
        when(notificationHistoryService.getAllNotificationHistories(any(Pageable.class))).thenThrow(new RuntimeException("Error"));

        CompletableFuture<ResponseEntity<PagedModel<NotificationHistoryDto>>> response = notificationController.getAllNotificationHistories(pageable);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationHistoryService, times(1)).getAllNotificationHistories(pageable);
    }

    @Test
    void createNotificationHistory_Success_ReturnsCreated() {
        when(notificationHistoryService.createNotificationHistory(any(NotificationHistoryDto.class))).thenReturn(notificationHistoryDto);

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.createNotificationHistory(notificationHistoryDto);
        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(notificationHistoryDto, response.join().getBody());
        verify(notificationHistoryService, times(1)).createNotificationHistory(notificationHistoryDto);
    }

    @Test
    void createNotificationHistory_Failure_ReturnsBadRequest() {
        when(notificationHistoryService.createNotificationHistory(any(NotificationHistoryDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.createNotificationHistory(notificationHistoryDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationHistoryService, times(1)).createNotificationHistory(notificationHistoryDto);
    }

    @Test
    void updateNotificationHistory_Success_ReturnsUpdatedHistory() {
        when(notificationHistoryService.updateNotificationHistory(anyLong(), any(NotificationHistoryDto.class))).thenReturn(notificationHistoryDto);

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.updateNotificationHistory(1L, notificationHistoryDto);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationHistoryDto, response.join().getBody());
        verify(notificationHistoryService, times(1)).updateNotificationHistory(1L, notificationHistoryDto);
    }

    @Test
    void updateNotificationHistory_NotFound_ReturnsNotFound() {
        when(notificationHistoryService.updateNotificationHistory(anyLong(), any(NotificationHistoryDto.class))).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationHistoryDto>> response = notificationController.updateNotificationHistory(1L, notificationHistoryDto);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationHistoryService, times(1)).updateNotificationHistory(1L, notificationHistoryDto);
    }

    @Test
    void deleteNotificationHistory_Success_ReturnsNoContent() {
        doNothing().when(notificationHistoryService).deleteNotificationHistory(anyLong());

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationHistory(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(notificationHistoryService, times(1)).deleteNotificationHistory(1L);
    }

    @Test
    void deleteNotificationHistory_NotFound_ReturnsNotFound() {
        doThrow(new RuntimeException("Not found")).when(notificationHistoryService).deleteNotificationHistory(anyLong());

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationHistory(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(notificationHistoryService, times(1)).deleteNotificationHistory(1L);
    }

    @Test
    void getNotificationMessageVersion_Success_ReturnsNotificationMessageVersionDto() {
        when(notificationMessageVersionService.getNotificationMessageVersion(anyLong())).thenReturn(notificationMessageVersionDto);

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.getNotificationMessageVersion(1L);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationMessageVersionDto, response.join().getBody());
        verify(notificationMessageVersionService, times(1)).getNotificationMessageVersion(1L);
    }

    @Test
    void getNotificationMessageVersion_NotFound_ReturnsNotFound() {
        when(notificationMessageVersionService.getNotificationMessageVersion(anyLong())).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.getNotificationMessageVersion(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationMessageVersionService, times(1)).getNotificationMessageVersion(1L);
    }

    @Test
    void getAllNotificationMessageVersions_Success_ReturnsPagedModel() {
        Page<NotificationMessageVersionDto> page = new PageImpl<>(Collections.singletonList(notificationMessageVersionDto));
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<NotificationMessageVersionDto> pagedModel = PagedModel.of(page.getContent(), metadata);
        when(notificationMessageVersionService.getAllNotificationMessageVersions(any(Pageable.class))).thenReturn(pagedModel);

        CompletableFuture<ResponseEntity<PagedModel<NotificationMessageVersionDto>>> response = notificationController.getAllNotificationMessageVersions(pageable);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(pagedModel, response.join().getBody());
        verify(notificationMessageVersionService, times(1)).getAllNotificationMessageVersions(pageable);
    }

    @Test
    void getAllNotificationMessageVersions_Failure_ReturnsInternalServerError() {
        when(notificationMessageVersionService.getAllNotificationMessageVersions(any(Pageable.class))).thenThrow(new RuntimeException("Error"));

        CompletableFuture<ResponseEntity<PagedModel<NotificationMessageVersionDto>>> response = notificationController.getAllNotificationMessageVersions(pageable);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationMessageVersionService, times(1)).getAllNotificationMessageVersions(pageable);
    }

    @Test
    void createNotificationMessageVersion_Success_ReturnsCreated() {
        when(notificationMessageVersionService.createNotificationMessageVersion(any(NotificationMessageVersionDto.class))).thenReturn(notificationMessageVersionDto);

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.createNotificationMessageVersion(notificationMessageVersionDto);
        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(notificationMessageVersionDto, response.join().getBody());
        verify(notificationMessageVersionService, times(1)).createNotificationMessageVersion(notificationMessageVersionDto);
    }

    @Test
    void createNotificationMessageVersion_Failure_ReturnsBadRequest() {
        when(notificationMessageVersionService.createNotificationMessageVersion(any(NotificationMessageVersionDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.createNotificationMessageVersion(notificationMessageVersionDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationMessageVersionService, times(1)).createNotificationMessageVersion(notificationMessageVersionDto);
    }

    @Test
    void updateNotificationMessageVersion_Success_ReturnsUpdatedVersion() {
        when(notificationMessageVersionService.updateNotificationMessageVersion(anyLong(), any(NotificationMessageVersionDto.class))).thenReturn(notificationMessageVersionDto);

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.updateNotificationMessageVersion(1L, notificationMessageVersionDto);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationMessageVersionDto, response.join().getBody());
        verify(notificationMessageVersionService, times(1)).updateNotificationMessageVersion(1L, notificationMessageVersionDto);
    }

    @Test
    void updateNotificationMessageVersion_NotFound_ReturnsNotFound() {
        when(notificationMessageVersionService.updateNotificationMessageVersion(anyLong(), any(NotificationMessageVersionDto.class))).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> response = notificationController.updateNotificationMessageVersion(1L, notificationMessageVersionDto);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationMessageVersionService, times(1)).updateNotificationMessageVersion(1L, notificationMessageVersionDto);
    }

    @Test
    void deleteNotificationMessageVersion_Success_ReturnsNoContent() {
        doNothing().when(notificationMessageVersionService).deleteNotificationMessageVersion(anyLong());

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationMessageVersion(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(notificationMessageVersionService, times(1)).deleteNotificationMessageVersion(1L);
    }

    @Test
    void deleteNotificationMessageVersion_NotFound_ReturnsNotFound() {
        doThrow(new RuntimeException("Not found")).when(notificationMessageVersionService).deleteNotificationMessageVersion(anyLong());

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationMessageVersion(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(notificationMessageVersionService, times(1)).deleteNotificationMessageVersion(1L);
    }

    @Test
    void getNotificationUserStatus_Success_ReturnsNotificationUserStatusDto() {
        when(notificationUserStatusService.getNotificationUserStatus(anyLong())).thenReturn(notificationUserStatusDto);

        CompletableFuture<ResponseEntity<NotificationUserStatusDto>> response = notificationController.getNotificationUserStatus(1L);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationUserStatusDto, response.join().getBody());
        verify(notificationUserStatusService, times(1)).getNotificationUserStatus(1L);
    }

    @Test
    void getNotificationUserStatus_NotFound_ReturnsNotFound() {
        when(notificationUserStatusService.getNotificationUserStatus(anyLong())).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationUserStatusDto>> response = notificationController.getNotificationUserStatus(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationUserStatusService, times(1)).getNotificationUserStatus(1L);
    }

    @Test
    void getAllNotificationUserStatuses_Success_ReturnsPagedModel() {
        Page<NotificationUserStatusDto> page = new PageImpl<>(Collections.singletonList(notificationUserStatusDto));
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel<NotificationUserStatusDto> pagedModel = PagedModel.of(page.getContent(), metadata);
        when(notificationUserStatusService.getAllNotificationUserStatuses(any(Pageable.class))).thenReturn(pagedModel);

        CompletableFuture<ResponseEntity<PagedModel<NotificationUserStatusDto>>> response = notificationController.getAllNotificationUserStatuses(pageable);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(pagedModel, response.join().getBody());
        verify(notificationUserStatusService, times(1)).getAllNotificationUserStatuses(pageable);
    }

    @Test
    void getAllNotificationUserStatuses_Failure_ReturnsInternalServerError() {
        when(notificationUserStatusService.getAllNotificationUserStatuses(any(Pageable.class))).thenThrow(new RuntimeException("Error"));

        CompletableFuture<ResponseEntity<PagedModel<NotificationUserStatusDto>>> response = notificationController.getAllNotificationUserStatuses(pageable);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationUserStatusService, times(1)).getAllNotificationUserStatuses(pageable);
    }

    @Test
    void createNotificationUserStatus_Success_ReturnsCreated() {
        when(notificationUserStatusService.createNotificationUserStatus(any(NotificationUserStatusDto.class))).thenReturn(notificationUserStatusDto);

        CompletableFuture<ResponseEntity<NotificationUserStatusDto>> response = notificationController.createNotificationUserStatus(notificationUserStatusDto);
        assertEquals(HttpStatus.CREATED, response.join().getStatusCode());
        assertEquals(notificationUserStatusDto, response.join().getBody());
        verify(notificationUserStatusService, times(1)).createNotificationUserStatus(notificationUserStatusDto);
    }

    @Test
    void createNotificationUserStatus_Failure_ReturnsBadRequest() {
        when(notificationUserStatusService.createNotificationUserStatus(any(NotificationUserStatusDto.class))).thenThrow(new RuntimeException("Bad request"));

        CompletableFuture<ResponseEntity<NotificationUserStatusDto>> response = notificationController.createNotificationUserStatus(notificationUserStatusDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationUserStatusService, times(1)).createNotificationUserStatus(notificationUserStatusDto);
    }

    @Test
    void updateNotificationUserStatus_Success_ReturnsUpdatedStatus() {
        when(notificationUserStatusService.updateNotificationUserStatus(anyLong(), any(NotificationUserStatusDto.class))).thenReturn(notificationUserStatusDto);

        CompletableFuture<ResponseEntity<NotificationUserStatusDto>> response = notificationController.updateNotificationUserStatus(1L, notificationUserStatusDto);
        assertEquals(HttpStatus.OK, response.join().getStatusCode());
        assertEquals(notificationUserStatusDto, response.join().getBody());
        verify(notificationUserStatusService, times(1)).updateNotificationUserStatus(1L, notificationUserStatusDto);
    }

    @Test
    void updateNotificationUserStatus_NotFound_ReturnsNotFound() {
        when(notificationUserStatusService.updateNotificationUserStatus(anyLong(), any(NotificationUserStatusDto.class))).thenThrow(new RuntimeException("Not found"));

        CompletableFuture<ResponseEntity<NotificationUserStatusDto>> response = notificationController.updateNotificationUserStatus(1L, notificationUserStatusDto);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        assertNull(response.join().getBody());
        verify(notificationUserStatusService, times(1)).updateNotificationUserStatus(1L, notificationUserStatusDto);
    }

    @Test
    void deleteNotificationUserStatus_Success_ReturnsNoContent() {
        doNothing().when(notificationUserStatusService).deleteNotificationUserStatus(anyLong());

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationUserStatus(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.join().getStatusCode());
        verify(notificationUserStatusService, times(1)).deleteNotificationUserStatus(1L);
    }

    @Test
    void deleteNotificationUserStatus_NotFound_ReturnsNotFound() {
        doThrow(new RuntimeException("Not found")).when(notificationUserStatusService).deleteNotificationUserStatus(anyLong());

        CompletableFuture<ResponseEntity<Void>> response = notificationController.deleteNotificationUserStatus(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.join().getStatusCode());
        verify(notificationUserStatusService, times(1)).deleteNotificationUserStatus(1L);
    }
}
