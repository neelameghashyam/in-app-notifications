package com.codebook.notification_system.controller;

import com.codebook.notification_system.dto.*;
import com.codebook.notification_system.service.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationHistoryService notificationHistoryService;
    private final NotificationMessageVersionService notificationMessageVersionService;
    private final NotificationUserStatusService notificationUserStatusService;

    public NotificationController(
            NotificationService notificationService,
            NotificationHistoryService notificationHistoryService,
            NotificationMessageVersionService notificationMessageVersionService,
            NotificationUserStatusService notificationUserStatusService) {
        this.notificationService = notificationService;
        this.notificationHistoryService = notificationHistoryService;
        this.notificationMessageVersionService = notificationMessageVersionService;
        this.notificationUserStatusService = notificationUserStatusService;
    }

    // Notification Endpoints
    @GetMapping("/notifications")
    public CompletableFuture<ResponseEntity<PagedModel<NotificationDto>>> getAllNotifications(Pageable pageable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PagedModel<NotificationDto> notifications = notificationService.getAllNotifications(pageable);
                return ResponseEntity.ok(notifications);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }

    @GetMapping("/notifications/{id}")
    public CompletableFuture<ResponseEntity<NotificationDto>> getNotification(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationDto notificationDto = notificationService.getNotification(id);
                return ResponseEntity.ok(notificationDto);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @PostMapping("/notifications")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ResponseEntity<NotificationDto>> createNotification(@Valid @RequestBody NotificationDto notificationDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationDto createdNotification = notificationService.createNotification(notificationDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        });
    }

    @PutMapping("/notifications/{id}")
    public CompletableFuture<ResponseEntity<NotificationDto>> updateNotification(@PathVariable Long id, @Valid @RequestBody NotificationDto notificationDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationDto updatedNotification = notificationService.updateNotification(id, notificationDto);
                return ResponseEntity.ok(updatedNotification);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @DeleteMapping("/notifications/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteNotification(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                notificationService.deleteNotification(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }

    // NotificationHistory Endpoints
    @GetMapping("/notification-history")
    public CompletableFuture<ResponseEntity<PagedModel<NotificationHistoryDto>>> getAllNotificationHistories(Pageable pageable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PagedModel<NotificationHistoryDto> histories = notificationHistoryService.getAllNotificationHistories(pageable);
                return ResponseEntity.ok(histories);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }

    @GetMapping("/notification-history/{id}")
    public CompletableFuture<ResponseEntity<NotificationHistoryDto>> getNotificationHistory(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationHistoryDto notificationHistoryDto = notificationHistoryService.getNotificationHistory(id);
                return ResponseEntity.ok(notificationHistoryDto);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @PostMapping("/notification-history")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ResponseEntity<NotificationHistoryDto>> createNotificationHistory(@Valid @RequestBody NotificationHistoryDto notificationHistoryDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationHistoryDto createdNotificationHistory = notificationHistoryService.createNotificationHistory(notificationHistoryDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdNotificationHistory);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        });
    }

    @PutMapping("/notification-history/{id}")
    public CompletableFuture<ResponseEntity<NotificationHistoryDto>> updateNotificationHistory(@PathVariable Long id, @Valid @RequestBody NotificationHistoryDto notificationHistoryDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationHistoryDto updatedNotificationHistory = notificationHistoryService.updateNotificationHistory(id, notificationHistoryDto);
                return ResponseEntity.ok(updatedNotificationHistory);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @DeleteMapping("/notification-history/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteNotificationHistory(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                notificationHistoryService.deleteNotificationHistory(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }

    // NotificationMessageVersion Endpoints
    @GetMapping("/notification-message-versions")
    public CompletableFuture<ResponseEntity<PagedModel<NotificationMessageVersionDto>>> getAllNotificationMessageVersions(Pageable pageable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PagedModel<NotificationMessageVersionDto> versions = notificationMessageVersionService.getAllNotificationMessageVersions(pageable);
                return ResponseEntity.ok(versions);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }

    @GetMapping("/notification-message-versions/{id}")
    public CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> getNotificationMessageVersion(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationMessageVersionDto notificationMessageVersionDto = notificationMessageVersionService.getNotificationMessageVersion(id);
                return ResponseEntity.ok(notificationMessageVersionDto);
                } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @PostMapping("/notification-message-versions")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> createNotificationMessageVersion(@Valid @RequestBody NotificationMessageVersionDto notificationMessageVersionDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationMessageVersionDto createdNotificationMessageVersion = notificationMessageVersionService.createNotificationMessageVersion(notificationMessageVersionDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdNotificationMessageVersion);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        });
    }

    @PutMapping("/notification-message-versions/{id}")
    public CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> updateNotificationMessageVersion(@PathVariable Long id, @Valid @RequestBody NotificationMessageVersionDto notificationMessageVersionDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationMessageVersionDto updatedNotificationMessageVersion = notificationMessageVersionService.updateNotificationMessageVersion(id, notificationMessageVersionDto);
                return ResponseEntity.ok(updatedNotificationMessageVersion);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @DeleteMapping("/notification-message-versions/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteNotificationMessageVersion(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                notificationMessageVersionService.deleteNotificationMessageVersion(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }

    // NotificationUserStatus Endpoints
    @GetMapping("/notification-user-status")
    public CompletableFuture<ResponseEntity<PagedModel<NotificationUserStatusDto>>> getAllNotificationUserStatuses(Pageable pageable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PagedModel<NotificationUserStatusDto> statuses = notificationUserStatusService.getAllNotificationUserStatuses(pageable);
                return ResponseEntity.ok(statuses);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        });
    }

    @GetMapping("/notification-user-status/{id}")
    public CompletableFuture<ResponseEntity<NotificationUserStatusDto>> getNotificationUserStatus(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationUserStatusDto notificationUserStatusDto = notificationUserStatusService.getNotificationUserStatus(id);
                return ResponseEntity.ok(notificationUserStatusDto);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @PostMapping("/notification-user-status")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ResponseEntity<NotificationUserStatusDto>> createNotificationUserStatus(@Valid @RequestBody NotificationUserStatusDto notificationUserStatusDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationUserStatusDto createdNotificationUserStatus = notificationUserStatusService.createNotificationUserStatus(notificationUserStatusDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdNotificationUserStatus);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        });
    }

    @PutMapping("/notification-user-status/{id}")
    public CompletableFuture<ResponseEntity<NotificationUserStatusDto>> updateNotificationUserStatus(@PathVariable Long id, @Valid @RequestBody NotificationUserStatusDto notificationUserStatusDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationUserStatusDto updatedNotificationUserStatus = notificationUserStatusService.updateNotificationUserStatus(id, notificationUserStatusDto);
                return ResponseEntity.ok(updatedNotificationUserStatus);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @DeleteMapping("/notification-user-status/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteNotificationUserStatus(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                notificationUserStatusService.deleteNotificationUserStatus(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }
}