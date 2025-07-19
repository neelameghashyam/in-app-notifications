package com.codebook.notification_system.service.impl;

import com.codebook.notification_system.dto.UserNotificationDismissedDto;
import com.codebook.notification_system.entity.UserNotificationDismissed;
import com.codebook.notification_system.mapper.UserNotificationDismissedMapper;
import com.codebook.notification_system.repository.UserNotificationDismissedRepository;
import com.codebook.notification_system.service.UserNotificationDismissedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNotificationDismissedServiceImpl implements UserNotificationDismissedService {

    private final UserNotificationDismissedRepository userNotificationDismissedRepository;
    private final UserNotificationDismissedMapper userNotificationDismissedMapper;

    public UserNotificationDismissedServiceImpl(UserNotificationDismissedRepository userNotificationDismissedRepository,
                                                UserNotificationDismissedMapper userNotificationDismissedMapper) {
        this.userNotificationDismissedRepository = userNotificationDismissedRepository;
        this.userNotificationDismissedMapper = userNotificationDismissedMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserNotificationDismissedDto getUserNotificationDismissed(Long id) {
        UserNotificationDismissed userNotificationDismissed = userNotificationDismissedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserNotificationDismissed not found with id: " + id));
        return userNotificationDismissedMapper.toDto(userNotificationDismissed);
    }

    @Override
    @Transactional
    public UserNotificationDismissedDto createUserNotificationDismissed(UserNotificationDismissedDto userNotificationDismissedDto) {
        UserNotificationDismissed userNotificationDismissed = userNotificationDismissedMapper.toEntity(userNotificationDismissedDto);
        UserNotificationDismissed savedUserNotificationDismissed = userNotificationDismissedRepository.save(userNotificationDismissed);
        return userNotificationDismissedMapper.toDto(savedUserNotificationDismissed);
    }

    @Override
    @Transactional
    public void deleteUserNotificationDismissed(Long id) {
        UserNotificationDismissed userNotificationDismissed = userNotificationDismissedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserNotificationDismissed not found with id: " + id));
        userNotificationDismissedRepository.deleteById(id);
    }
}