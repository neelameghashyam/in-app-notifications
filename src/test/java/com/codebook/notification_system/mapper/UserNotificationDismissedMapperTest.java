package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.UserNotificationDismissedDto;
import com.codebook.notification_system.entity.UserNotificationDismissed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserNotificationDismissedMapperTest {

    @Autowired
    private UserNotificationDismissedMapper userNotificationDismissedMapper;

    @Test
    void toDto_shouldMapEntityToDto() {
        // Arrange
        UserNotificationDismissed entity = new UserNotificationDismissed();
        entity.setId(1L);
        entity.setNotificationId(2L);
        entity.setUserId(3L);
        entity.setDismissedAt(LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        UserNotificationDismissedDto dto = userNotificationDismissedMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(2L, dto.notificationId());
        assertEquals(3L, dto.userId());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), dto.dismissedAt());
    }

    @Test
    void toDto_shouldReturnNullWhenEntityIsNull() {
        // Act
        UserNotificationDismissedDto dto = userNotificationDismissedMapper.toDto(null);

        // Assert
        assertNull(dto);
    }

    @Test
    void toEntity_shouldMapDtoToEntity() {
        // Arrange
        UserNotificationDismissedDto dto = new UserNotificationDismissedDto(
                1L, 2L, 3L, LocalDateTime.of(2025, 7, 17, 14, 25));

        // Act
        UserNotificationDismissed entity = userNotificationDismissedMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(2L, entity.getNotificationId());
        assertEquals(3L, entity.getUserId());
        assertEquals(LocalDateTime.of(2025, 7, 17, 14, 25), entity.getDismissedAt());
    }

    @Test
    void toEntity_shouldReturnNullWhenDtoIsNull() {
        // Act
        UserNotificationDismissed entity = userNotificationDismissedMapper.toEntity(null);

        // Assert
        assertNull(entity);
    }
}
