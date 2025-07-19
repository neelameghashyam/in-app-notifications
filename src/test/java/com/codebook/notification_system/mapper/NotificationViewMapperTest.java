package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationViewDto;
import com.codebook.notification_system.entity.NotificationView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationViewMapperTest {

    @Autowired
    private NotificationViewMapper notificationViewMapper;

    @Test
    void toDto_shouldMapEntityToDto() {
        // Arrange
        NotificationView entity = new NotificationView();
        entity.setId(1L);
        entity.setNotificationId(2L);
        entity.setUserId(3L);
        entity.setLastViewedAt(LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        NotificationViewDto dto = notificationViewMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(2L, dto.notificationId());
        assertEquals(3L, dto.userId());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), dto.lastViewedAt());
    }

    @Test
    void toDto_shouldReturnNullWhenEntityIsNull() {
        // Act
        NotificationViewDto dto = notificationViewMapper.toDto(null);

        // Assert
        assertNull(dto);
    }

    @Test
    void toEntity_shouldMapDtoToEntity() {
        // Arrange
        NotificationViewDto dto = new NotificationViewDto(
                1L, 2L, 3L, LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        NotificationView entity = notificationViewMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(2L, entity.getNotificationId());
        assertEquals(3L, entity.getUserId());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), entity.getLastViewedAt());
    }

    @Test
    void toEntity_shouldReturnNullWhenDtoIsNull() {
        // Act
        NotificationView entity = notificationViewMapper.toEntity(null);

        // Assert
        assertNull(entity);
    }
}
