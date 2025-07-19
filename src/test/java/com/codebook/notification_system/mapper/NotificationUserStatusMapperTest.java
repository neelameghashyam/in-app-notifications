package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationUserStatusDto;
import com.codebook.notification_system.entity.NotificationUserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationUserStatusMapperTest {

    private NotificationUserStatusMapper mapper;

    private NotificationUserStatus notificationUserStatus;
    private NotificationUserStatusDto notificationUserStatusDto;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(NotificationUserStatusMapper.class);

        notificationUserStatus = new NotificationUserStatus();
        notificationUserStatus.setId(1L);
        notificationUserStatus.setNotificationId(1L);
        notificationUserStatus.setUserId(1L);
        notificationUserStatus.setSeenAt(LocalDateTime.now());
        notificationUserStatus.setDismissedAt(null);
        notificationUserStatus.setDontShowAgain(false);
        notificationUserStatus.setLastShownDate(LocalDate.now());
        notificationUserStatus.setCreatedAt(LocalDateTime.now());

        notificationUserStatusDto = new NotificationUserStatusDto(1L, 1L, 1L, notificationUserStatus.getSeenAt(), null, false, LocalDate.now(), notificationUserStatus.getCreatedAt());
    }

    @Test
    void toDto_Success_MapsEntityToDto() {
        NotificationUserStatusDto result = mapper.toDto(notificationUserStatus);
        assertEquals(notificationUserStatusDto, result);
        assertEquals(notificationUserStatus.getId(), result.id());
        assertEquals(notificationUserStatus.getNotificationId(), result.notificationId());
        assertEquals(notificationUserStatus.getUserId(), result.userId());
        assertEquals(notificationUserStatus.getSeenAt(), result.seenAt());
        assertEquals(notificationUserStatus.getDismissedAt(), result.dismissedAt());
        assertEquals(notificationUserStatus.getDontShowAgain(), result.dontShowAgain());
        assertEquals(notificationUserStatus.getLastShownDate(), result.lastShownDate());
        assertEquals(notificationUserStatus.getCreatedAt(), result.createdAt());
    }

    @Test
    void toDto_NullEntity_ReturnsNull() {
        NotificationUserStatusDto result = mapper.toDto(null);
        assertNull(result);
    }

    @Test
    void toEntity_Success_MapsDtoToEntity() {
        NotificationUserStatus result = mapper.toEntity(notificationUserStatusDto);
        assertEquals(notificationUserStatus.getId(), result.getId());
        assertEquals(notificationUserStatus.getNotificationId(), result.getNotificationId());
        assertEquals(notificationUserStatus.getUserId(), result.getUserId());
        assertEquals(notificationUserStatus.getSeenAt(), result.getSeenAt());
        assertEquals(notificationUserStatus.getDismissedAt(), result.getDismissedAt());
        assertEquals(notificationUserStatus.getDontShowAgain(), result.getDontShowAgain());
        assertEquals(notificationUserStatus.getLastShownDate(), result.getLastShownDate());
        assertEquals(notificationUserStatus.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void toEntity_NullDto_ReturnsNull() {
        NotificationUserStatus result = mapper.toEntity(null);
        assertNull(result);
    }

    @Test
    void toDto_PartialFields_MapsCorrectly() {
        NotificationUserStatus partialEntity = new NotificationUserStatus();
        partialEntity.setId(1L);
        partialEntity.setNotificationId(1L);
        partialEntity.setUserId(1L);

        NotificationUserStatusDto result = mapper.toDto(partialEntity);
        assertEquals(1L, result.id());
        assertEquals(1L, result.notificationId());
        assertEquals(1L, result.userId());
        assertNull(result.seenAt());
        assertNull(result.dismissedAt());
        assertEquals(false, result.dontShowAgain()); // Expect default value of false
        assertNull(result.lastShownDate());
        assertNull(result.createdAt());
    }

    @Test
    void toEntity_PartialFields_MapsCorrectly() {
        NotificationUserStatusDto partialDto = new NotificationUserStatusDto(1L, 1L, 1L, null, null, null, null, null);

        NotificationUserStatus result = mapper.toEntity(partialDto);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getNotificationId());
        assertEquals(1L, result.getUserId());
        assertNull(result.getSeenAt());
        assertNull(result.getDismissedAt());
        assertNull(result.getDontShowAgain()); // DTO can pass null, no default value in DTO
        assertNull(result.getLastShownDate());
        assertNull(result.getCreatedAt());
    }
}