package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationMessageVersionDto;
import com.codebook.notification_system.entity.NotificationMessageVersion;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMessageVersionMapper {
    NotificationMessageVersionDto toDto(NotificationMessageVersion notificationMessageVersion);
    NotificationMessageVersion toEntity(NotificationMessageVersionDto notificationMessageVersionDto);
}