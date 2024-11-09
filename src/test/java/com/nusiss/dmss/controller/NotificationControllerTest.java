package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Notifications;
import com.nusiss.dmss.service.NotificationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @Mock
    private NotificationsService notificationsService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllNotifications() {
        // 准备数据
        when(notificationsService.getAllNotifications()).thenReturn(Arrays.asList(
                new Notifications(),
                new Notifications()
        ));

        // 执行操作
        ResponseEntity<ApiResponse<List<Notifications>>> response = notificationController.getAllNotifications();

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Notifications retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getNotificationById() {
        // 准备数据
        Integer id = 1;
        Notifications notification = new Notifications();
        notification.setNotificationId(id);
        when(notificationsService.getNotificationById(id)).thenReturn(Optional.of(notification));

        // 执行操作
        ResponseEntity<ApiResponse<Notifications>> response = notificationController.getNotificationById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Notification retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getNotificationId());
    }

    @Test
    void getNotificationById_NotFound() {
        // 准备数据
        Integer id = 1;
        when(notificationsService.getNotificationById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Notifications>> response = notificationController.getNotificationById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Notification not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void getNotificationsByUserId() {
        // 准备数据
        Integer userId = 1;
        when(notificationsService.getNotificationsByUserId(userId)).thenReturn(Arrays.asList(
                new Notifications(),
                new Notifications()
        ));

        // 执行操作
        ResponseEntity<ApiResponse<List<Notifications>>> response = notificationController.getNotificationsByUserId(userId);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Notifications retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void createNotification() {
        // 准备数据
        Notifications notification = new Notifications();
        notification.setNotificationId(1);
        when(notificationsService.saveNotification(notification)).thenReturn(notification);

        // 执行操作
        ResponseEntity<ApiResponse<Notifications>> response = notificationController.createNotification(notification);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Notifications created successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().getNotificationId());
    }

    @Test
    void updateNotification() {
        // 准备数据
        Integer id = 1;
        Notifications updatedNotification = new Notifications();
        updatedNotification.setNotificationId(id);
        when(notificationsService.getNotificationById(id)).thenReturn(Optional.of(new Notifications()));
        when(notificationsService.saveNotification(updatedNotification)).thenReturn(updatedNotification);

        // 执行操作
        ResponseEntity<ApiResponse<Notifications>> response = notificationController.updateNotification(id, updatedNotification);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Notification updated successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getNotificationId());
    }

    @Test
    void updateNotification_NotFound() {
        // 准备数据
        Integer id = 1;
        Notifications updatedNotification = new Notifications();
        updatedNotification.setNotificationId(id);
        when(notificationsService.getNotificationById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Notifications>> response = notificationController.updateNotification(id, updatedNotification);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Notification not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteNotification() {
        // 准备数据
        Integer id = 1;
        when(notificationsService.getNotificationById(id)).thenReturn(Optional.of(new Notifications()));

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = notificationController.deleteNotification(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Notification deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteNotification_NotFound() {
        // 准备数据
        Integer id = 1;
        when(notificationsService.getNotificationById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = notificationController.deleteNotification(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Notification not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void getNotificationsByUserIdPaginated() {
        // 准备数据
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Notifications> notifications = new PageImpl<>(Arrays.asList(
                new Notifications(),
                new Notifications()
        ));
        when(notificationsService.getAllNotificationsByPage(pageable)).thenReturn(notifications);

        // 执行操作
        ResponseEntity<ApiResponse<Page<Notifications>>> response = notificationController.getNotificationsByUserIdPaginated(page, size);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Paginated notifications retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().getContent().size());
    }
}