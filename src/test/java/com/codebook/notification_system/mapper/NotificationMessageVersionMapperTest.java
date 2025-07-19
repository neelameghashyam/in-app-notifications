package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationMessageVersionDto;
import com.codebook.notification_system.entity.NotificationMessageVersion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationMessageVersionMapperTest {

    @Autowired
    private NotificationMessageVersionMapper notificationMessageVersionMapper;

    @Test
    void toDto_shouldMapEntityToDto() {
        // Arrange
        NotificationMessageVersion entity = new NotificationMessageVersion();
        entity.setVersionId(1L);
        entity.setNotificationId(2L);
        entity.setMessage("Test Message");
        entity.setVersionNumber(1);
        entity.setUpdatedBy("user1");
        entity.setUpdatedAt(LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        NotificationMessageVersionDto dto = notificationMessageVersionMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.versionId());
        assertEquals(2L, dto.notificationId());
        assertEquals("Test Message", dto.message());
        assertEquals(1, dto.versionNumber());
        assertEquals("user1", dto.updatedBy());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), dto.updatedAt());
    }

    @Test
    void toDto_shouldReturnNullWhenEntityIsNull() {
        // Act
        NotificationMessageVersionDto dto = notificationMessageVersionMapper.toDto(null);

        // Assert
        assertNull(dto);
    }

    @Test
    void toEntity_shouldMapDtoToEntity() {
        // Arrange
        NotificationMessageVersionDto dto = new NotificationMessageVersionDto(
                1L, 2L, "Test Message", 1, "user1",
                LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        NotificationMessageVersion entity = notificationMessageVersionMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(1L, entity.getVersionId());
        assertEquals(2L, entity.getNotificationId());
        assertEquals("Test Message", entity.getMessage());
        assertEquals(1, entity.getVersionNumber());
        assertEquals("user1", entity.getUpdatedBy());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), entity.getUpdatedAt());
    }

    @Test
    void toEntity_shouldReturnNullWhenDtoIsNull() {
        // Act
        NotificationMessageVersion entity = notificationMessageVersionMapper.toEntity(null);

        // Assert
        assertNull(entity);
    }
}