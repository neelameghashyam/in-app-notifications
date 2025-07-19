package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationClientDto;
import com.codebook.notification_system.entity.NotificationClient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationClientMapper {
    NotificationClientDto toDto(NotificationClient notificationClient);
    NotificationClient toEntity(NotificationClientDto notificationClientDto);
}