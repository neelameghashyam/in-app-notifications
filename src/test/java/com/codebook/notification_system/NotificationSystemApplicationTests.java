package com.codebook.notification_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class NotificationSystemApplicationTests {

    @Test
    void contextLoads() {
        // Verifies that the Spring application context loads successfully
    }

    @Test
    void main_shouldStartApplicationWithoutException() {
        // Arrange
        String[] args = new String[]{};

        // Act & Assert
        assertDoesNotThrow(() -> NotificationSystemApplication.main(args));
    }
}
