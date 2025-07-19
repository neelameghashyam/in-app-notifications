package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationClientDto;
import com.codebook.notification_system.entity.NotificationClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationClientMapperTest {

    @Autowired
    private NotificationClientMapper notificationClientMapper;

    @Test
    void toDto_shouldMapEntityToDto() {
        // Arrange
        NotificationClient entity = new NotificationClient();
        entity.setId(1L);
        entity.setNotificationId(2L);
        entity.setClientId(3L);

        // Act
        NotificationClientDto dto = notificationClientMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(2L, dto.notificationId());
        assertEquals(3L, dto.clientId());
    }

    @Test
    void toDto_shouldReturnNullWhenEntityIsNull() {
        // Act
        NotificationClientDto dto = notificationClientMapper.toDto(null);

        // Assert
        assertNull(dto);
    }

    @Test
    void toEntity_shouldMapDtoToEntity() {
        // Arrange
        NotificationClientDto dto = new NotificationClientDto(1L, 2L, 3L);

        // Act
        NotificationClient entity = notificationClientMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(2L, entity.getNotificationId());
        assertEquals(3L, entity.getClientId());
    }

    @Test
    void toEntity_shouldReturnNullWhenDtoIsNull() {
        // Act
        NotificationClient entity = notificationClientMapper.toEntity(null);

        // Assert
        assertNull(entity);
    }
}
