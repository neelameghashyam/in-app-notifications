package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.NotificationHistoryDto;
import com.codebook.notification_system.entity.NotificationHistory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationHistoryMapper {
    NotificationHistoryDto toDto(NotificationHistory notificationHistory);
    NotificationHistory toEntity(NotificationHistoryDto notificationHistoryDto);
}
