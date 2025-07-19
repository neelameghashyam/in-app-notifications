package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationDto;
import com.codebook.notification_system.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {
    NotificationDto toDto(Notification notification);
    Notification toEntity(NotificationDto notificationDto);
}