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
        entity.setHistoryId(1L);
        entity.setNotificationId(2L);
        entity.setUpdatedBy("user1");
        entity.setFieldName("title");
        entity.setAction("UPDATE");
        entity.setOldValue("old");
        entity.setNewValue("new");
        entity.setUpdatedAt(LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        NotificationHistoryDto dto = notificationHistoryMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.historyId());
        assertEquals(2L, dto.notificationId());
        assertEquals("user1", dto.updatedBy());
        assertEquals("title", dto.fieldName());
        assertEquals("UPDATE", dto.action());
        assertEquals("old", dto.oldValue());
        assertEquals("new", dto.newValue());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), dto.updatedAt());
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
                1L, 2L, "user1", "title", "UPDATE", "old", "new",
                LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        NotificationHistory entity = notificationHistoryMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(1L, entity.getHistoryId());
        assertEquals(2L, entity.getNotificationId());
        assertEquals("user1", entity.getUpdatedBy());
        assertEquals("title", entity.getFieldName());
        assertEquals("UPDATE", entity.getAction());
        assertEquals("old", entity.getOldValue());
        assertEquals("new", entity.getNewValue());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), entity.getUpdatedAt());
    }

    @Test
    void toEntity_shouldReturnNullWhenDtoIsNull() {
        // Act
        NotificationHistory entity = notificationHistoryMapper.toEntity(null);

        // Assert
        assertNull(entity);
    }
}
