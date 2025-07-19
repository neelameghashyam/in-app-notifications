package com.codebook.notification_system.mapper;

import com.codebook.notification_system.dto.UserNotificationDismissedDto;
import com.codebook.notification_system.entity.UserNotificationDismissed;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserNotificationDismissedMapper {
    UserNotificationDismissedDto toDto(UserNotificationDismissed userNotificationDismissed);
    UserNotificationDismissed toEntity(UserNotificationDismissedDto userNotificationDismissedDto);
}