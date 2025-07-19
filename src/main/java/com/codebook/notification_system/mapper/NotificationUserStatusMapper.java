package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationUserStatusDto;
import com.codebook.notification_system.entity.NotificationUserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationUserStatusMapper {
    NotificationUserStatusDto toDto(NotificationUserStatus notificationUserStatus);
    NotificationUserStatus toEntity(NotificationUserStatusDto notificationUserStatusDto);
}