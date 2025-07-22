package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationHistoryDto;
import com.codebook.notification_system.entity.NotificationHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationHistoryMapperTest {

    @Autowired
    private NotificationHistoryMapper notificationHistoryMapper;

    @Test
    void toDto_shouldMapEntityToDto() {
        // Arrange
        NotificationHistory entity = new NotificationHistory();
        entity.setId(1L);
        entity.setNotificationId(1L);
        entity.setUpdatedBy("user1");
        entity.setFieldName("title");
        entity.setAction("UPDATE");
        entity.setOldValue("Old Title");
        entity.setNewValue("New Title");
        entity.setUpdatedAt(LocalDateTime.of(2025, 7, 21, 19, 3));

        // Act
        NotificationHistoryDto dto = notificationHistoryMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(1L, dto.notificationId());
        assertEquals("user1", dto.updatedBy());
        assertEquals("title", dto.fieldName());
        assertEquals("UPDATE", dto.action());
        assertEquals("Old Title", dto.oldValue());
        assertEquals("New Title", dto.newValue());
        assertEquals(LocalDateTime.of(2025, 7, 21, 19, 3), dto.updatedAt());
    }

    @Test
    void toDto_shouldReturnNullWhenEntityIsNull() {
        // Act
        NotificationHistoryDto dto = notificationHistoryMapper.toDto(null);

        // Assert
        assertNull(dto);
    }

    @Test
    void toEntity_shouldMapDtoToEntity() {
        // Arrange
        NotificationHistoryDto dto = new NotificationHistoryDto(
                1L, 1L, "user1", "title", "UPDATE", "Old Title", "New Title",
                LocalDateTime.of(2025, 7, 21, 19, 3));

        // Act
        NotificationHistory entity = notificationHistoryMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(1L, entity.getNotificationId());
        assertEquals("user1", entity.getUpdatedBy());
        assertEquals("title", entity.getFieldName());
        assertEquals("UPDATE", entity.getAction());
        assertEquals("Old Title", entity.getOldValue());
        assertEquals("New Title", entity.getNewValue());
        assertEquals(LocalDateTime.of(2025, 7, 21, 19, 3), entity.getUpdatedAt());
    }

    @Test
    void toEntity_shouldReturnNullWhenDtoIsNull() {
        // Act
        NotificationHistory entity = notificationHistoryMapper.toEntity(null);

        // Assert
        assertNull(entity);
    }
}
