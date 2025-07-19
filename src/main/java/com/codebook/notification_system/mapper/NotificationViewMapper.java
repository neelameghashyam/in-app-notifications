package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationViewDto;
import com.codebook.notification_system.entity.NotificationView;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationViewMapper {
    NotificationViewDto toDto(NotificationView notificationView);
    NotificationView toEntity(NotificationViewDto notificationViewDto);
}