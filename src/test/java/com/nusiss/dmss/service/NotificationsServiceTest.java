package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.NotificationRepository;
import com.nusiss.dmss.entity.Notifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationsServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationsServiceImpl notificationsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllNotifications() {
        // 准备数据
        when(notificationRepository.findAll()).thenReturn(Arrays.asList(
                new Notifications(),
                new Notifications()
        ));

        // 执行操作
        List<Notifications> notifications = notificationsService.getAllNotifications();

        // 验证结果
        assertNotNull(notifications);
        assertEquals(2, notifications.size());
    }

    @Test
    void getNotificationById() {
        // 准备数据
        Integer id = 1;
        Notifications notification = new Notifications();
        notification.setNotificationId(id);
        when(notificationRepository.findById(id)).thenReturn(Optional.of(notification));

        // 执行操作
        Optional<Notifications> result = notificationsService.getNotificationById(id);

        // 验证结果
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getNotificationId());
    }

    @Test
    void getNotificationsByUserId() {
        // 准备数据
        Integer userId = 1;
        when(notificationRepository.findByUserId(userId)).thenReturn(Arrays.asList(
                new Notifications(),
                new Notifications()
        ));

        // 执行操作
        List<Notifications> notifications = notificationsService.getNotificationsByUserId(userId);

        // 验证结果
        assertNotNull(notifications);
        assertEquals(2, notifications.size());
    }

    @Test
    void saveNotification() {
        // 准备数据
        Notifications notification = new Notifications();
        notification.setNotificationId(1);
        when(notificationRepository.save(any(Notifications.class))).thenReturn(notification);

        // 执行操作
        Notifications savedNotification = notificationsService.saveNotification(notification);

        // 验证结果
        assertNotNull(savedNotification);
        assertEquals(1, savedNotification.getNotificationId());
    }

    @Test
    void deleteNotification() {
        // 准备数据
        Integer id = 1;

        // 执行操作
        notificationsService.deleteNotification(id);

        // 验证操作
        verify(notificationRepository, times(1)).deleteById(id);
    }

    @Test
    void getAllNotificationsByPage() {
        // 准备数据
        Pageable pageable = PageRequest.of(0, 10);
        Page<Notifications> page = new PageImpl<>(Arrays.asList(
                new Notifications(),
                new Notifications()
        ));
        when(notificationRepository.findAll(pageable)).thenReturn(page);

        // 执行操作
        Page<Notifications> result = notificationsService.getAllNotificationsByPage(pageable);

        // 验证结果
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}