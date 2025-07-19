package com.codebook.notification_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseConnectionTest implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Successfully connected to MySQL database: " + connection.getCatalog());
        } catch (Exception e) {
            System.err.println("Failed to connect to MySQL database: " + e.getMessage());
            throw e;
        }
    }
}