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
    private final NotificationViewService notificationViewService;
    private final UserNotificationDismissedService userNotificationDismissedService;

    public NotificationController(
            NotificationService notificationService,
            NotificationClientService notificationClientService,
            NotificationHistoryService notificationHistoryService,
            NotificationMessageVersionService notificationMessageVersionService,
            NotificationViewService notificationViewService,
            UserNotificationDismissedService userNotificationDismissedService) {
        this.notificationService = notificationService;
        this.notificationClientService = notificationClientService;
        this.notificationHistoryService = notificationHistoryService;
        this.notificationMessageVersionService = notificationMessageVersionService;
        this.notificationViewService = notificationViewService;
        this.userNotificationDismissedService = userNotificationDismissedService;
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

    // NotificationView Endpoints
    @GetMapping("/views/{id}")
    public CompletableFuture<ResponseEntity<NotificationViewDto>> getNotificationView(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationViewDto notificationViewDto = notificationViewService.getNotificationView(id);
                return ResponseEntity.ok(notificationViewDto);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @PostMapping("/views")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ResponseEntity<NotificationViewDto>> createNotificationView(@Valid @RequestBody NotificationViewDto notificationViewDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                NotificationViewDto createdNotificationView = notificationViewService.createNotificationView(notificationViewDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdNotificationView);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        });
    }

    @DeleteMapping("/views/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteNotificationView(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                notificationViewService.deleteNotificationView(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }

    // UserNotificationDismissed Endpoints
    @GetMapping("/dismissed/{id}")
    public CompletableFuture<ResponseEntity<UserNotificationDismissedDto>> getUserNotificationDismissed(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserNotificationDismissedDto userNotificationDismissedDto = userNotificationDismissedService.getUserNotificationDismissed(id);
                return ResponseEntity.ok(userNotificationDismissedDto);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        });
    }

    @PostMapping("/dismissed")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ResponseEntity<UserNotificationDismissedDto>> createUserNotificationDismissed(@Valid @RequestBody UserNotificationDismissedDto userNotificationDismissedDto) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserNotificationDismissedDto createdUserNotificationDismissed = userNotificationDismissedService.createUserNotificationDismissed(userNotificationDismissedDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdUserNotificationDismissed);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        });
    }

    @DeleteMapping("/dismissed/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteUserNotificationDismissed(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                userNotificationDismissedService.deleteUserNotificationDismissed(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        });
    }
}