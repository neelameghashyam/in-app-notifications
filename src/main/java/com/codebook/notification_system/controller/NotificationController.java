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
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationClientService notificationClientService;
    private final NotificationHistoryService notificationHistoryService;
    private final NotificationMessageVersionService notificationMessageVersionService;
    private final NotificationUserStatusService notificationUserStatusService;

    public NotificationController(
            NotificationService notificationService,
            NotificationClientService notificationClientService,
            NotificationHistoryService notificationHistoryService,
            NotificationMessageVersionService notificationMessageVersionService,
            NotificationUserStatusService notificationUserStatusService) {
        this.notificationService = notificationService;
        this.notificationClientService = notificationClientService;
        this.notificationHistoryService = notificationHistoryService;
        this.notificationMessageVersionService = notificationMessageVersionService;
        this.notificationUserStatusService = notificationUserStatusService;
    }

    // Notification Endpoints
    @GetMapping("/{id}")
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

    @GetMapping
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ResponseEntity<NotificationDto>> createNotification(@Valid @RequestBody NotificationDto notificationDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationDto createdNotification = notificationService.createNotification(notificationDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        });
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<NotificationDto>> updateNotification(@PathVariable Long id, @Valid @RequestBody NotificationDto notificationDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationDto updatedNotification = notificationService.updateNotification(id, notificationDto);
                return ResponseEntity.ok(updatedNotification);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @DeleteMapping("/{id}")
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

    // NotificationClient Endpoints
    @GetMapping("/clients/{id}")
    public CompletableFuture<ResponseEntity<NotificationClientDto>> getNotificationClient(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationClientDto notificationClientDto = notificationClientService.getNotificationClient(id);
                return ResponseEntity.ok(notificationClientDto);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ResponseEntity<NotificationClientDto>> createNotificationClient(@Valid @RequestBody NotificationClientDto notificationClientDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationClientDto createdNotificationClient = notificationClientService.createNotificationClient(notificationClientDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdNotificationClient);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        });
    }

    @PutMapping("/clients/{id}")
    public CompletableFuture<ResponseEntity<NotificationClientDto>> updateNotificationClient(@PathVariable Long id, @Valid @RequestBody NotificationClientDto notificationClientDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationClientDto updatedNotificationClient = notificationClientService.updateNotificationClient(id, notificationClientDto);
                return ResponseEntity.ok(updatedNotificationClient);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @DeleteMapping("/clients/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteNotificationClient(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                notificationClientService.deleteNotificationClient(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }

    // NotificationHistory Endpoints
    @GetMapping("/history/{historyId}")
    public CompletableFuture<ResponseEntity<NotificationHistoryDto>> getNotificationHistory(@PathVariable Long historyId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationHistoryDto notificationHistoryDto = notificationHistoryService.getNotificationHistory(historyId);
                return ResponseEntity.ok(notificationHistoryDto);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @PostMapping("/history")
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

    @DeleteMapping("/history/{historyId}")
    public CompletableFuture<ResponseEntity<Void>> deleteNotificationHistory(@PathVariable Long historyId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                notificationHistoryService.deleteNotificationHistory(historyId);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }

    // NotificationMessageVersion Endpoints
    @GetMapping("/message-versions/{versionId}")
    public CompletableFuture<ResponseEntity<NotificationMessageVersionDto>> getNotificationMessageVersion(@PathVariable Long versionId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationMessageVersionDto notificationMessageVersionDto = notificationMessageVersionService.getNotificationMessageVersion(versionId);
                return ResponseEntity.ok(notificationMessageVersionDto);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @PostMapping("/message-versions")
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

    @DeleteMapping("/message-versions/{versionId}")
    public CompletableFuture<ResponseEntity<Void>> deleteNotificationMessageVersion(@PathVariable Long versionId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                notificationMessageVersionService.deleteNotificationMessageVersion(versionId);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }

    // NotificationUserStatus Endpoints
    @GetMapping("/user-status/{id}")
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

    @GetMapping("/user-status")
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

    @PostMapping("/user-status")
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

    @PutMapping("/user-status/{id}")
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

    @DeleteMapping("/user-status/{id}")
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