package com.codebook.notification_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_user_status", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"notification_id", "user_id"})
})
public class NotificationUserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_id", nullable = false)
    private Long notificationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "seen_at")
    private LocalDateTime seenAt;

    @Column(name = "dismissed_at")
    private LocalDateTime dismissedAt;

    @Column(name = "dont_show_again")
    private Boolean dontShowAgain = false;

    @Column(name = "last_shown_date")
    private LocalDate lastShownDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getNotificationId() { return notificationId; }
    public void setNotificationId(Long notificationId) { this.notificationId = notificationId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDateTime getSeenAt() { return seenAt; }
    public void setSeenAt(LocalDateTime seenAt) { this.seenAt = seenAt; }
    public LocalDateTime getDismissedAt() { return dismissedAt; }
    public void setDismissedAt(LocalDateTime dismissedAt) { this.dismissedAt = dismissedAt; }
    public Boolean getDontShowAgain() { return dontShowAgain; }
    public void setDontShowAgain(Boolean dontShowAgain) { this.dontShowAgain = dontShowAgain; }
    public LocalDate getLastShownDate() { return lastShownDate; }
    public void setLastShownDate(LocalDate lastShownDate) { this.lastShownDate = lastShownDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}