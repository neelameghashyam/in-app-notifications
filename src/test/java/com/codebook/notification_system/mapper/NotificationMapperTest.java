package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationDto;
import com.codebook.notification_system.entity.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationMapperTest {

    @Autowired
    private NotificationMapper notificationMapper;

    @Test
    void toDto_shouldMapEntityToDto() {
        // Arrange
        Notification entity = new Notification();
        entity.setId(1L);
        entity.setTitle("Test Notification");
        entity.setMessage("Test Message");
        entity.setFromDate(LocalDate.of(2025, 7, 1));
        entity.setToDate(LocalDate.of(2025, 7, 31));
        entity.setDeletedAt(LocalDateTime.of(2025, 7, 17, 14, 25));
        entity.setDeletedBy("user1");
        entity.setStatus("ACTIVE");
        entity.setFrequency("DAILY");
        entity.setCreatedAt(LocalDateTime.of(2025, 7, 1, 10, 0));
        entity.setUpdatedAt(LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        NotificationDto dto = notificationMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("Test Notification", dto.title());
        assertEquals("Test Message", dto.message());
        assertEquals(LocalDate.of(2025, 7, 1), dto.fromDate());
        assertEquals(LocalDate.of(2025, 7, 31), dto.toDate());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), dto.deletedAt());
        assertEquals("user1", dto.deletedBy());
        assertEquals("ACTIVE", dto.status());
        assertEquals("DAILY", dto.frequency());
        assertEquals(LocalDateTime.of(2025, 7, 1, 10, 0), dto.createdAt());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), dto.updatedAt());
    }

    @Test
    void toDto_shouldReturnNullWhenEntityIsNull() {
        // Act
        NotificationDto dto = notificationMapper.toDto(null);

        // Assert
        assertNull(dto);
    }

    @Test
    void toEntity_shouldMapDtoToEntity() {
        // Arrange
        NotificationDto dto = new NotificationDto(
                1L, "Test Notification", "Test Message",
                LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 31),
                LocalDateTime.of(2025, 7, 17, 14, 25), "user1",
                "ACTIVE", "DAILY",
                LocalDateTime.of(2025, 7, 1, 10, 0),
                LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        Notification entity = notificationMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Test Notification", entity.getTitle());
        assertEquals("Test Message", entity.getMessage());
        assertEquals(LocalDate.of(2025, 7, 1), entity.getFromDate());
        assertEquals(LocalDate.of(2025, 7, 31), entity.getToDate());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), entity.getDeletedAt());
        assertEquals("user1", entity.getDeletedBy());
        assertEquals("ACTIVE", entity.getStatus());
        assertEquals("DAILY", entity.getFrequency());
        assertEquals(LocalDateTime.of(2025, 7, 1, 10, 0), entity.getCreatedAt());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), entity.getUpdatedAt());
    }

    @Test
    void toEntity_shouldReturnNullWhenDtoIsNull() {
        // Act
        Notification entity = notificationMapper.toEntity(null);

        // Assert
        assertNull(entity);
    }
}