package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Notifications;
import com.nusiss.dmss.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 通知管理
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationsService notificationsService;

    /**
     * 获取所有通知
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Notifications>>> getAllNotifications() {
        List<Notifications> notifications = notificationsService.getAllNotifications();
        return ResponseEntity.ok(new ApiResponse<>(true, "Notifications retrieved successfully", notifications));
    }

    /**
     * 根据id获取通知
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Notifications>> getNotificationById(@PathVariable Integer id) {
        Optional<Notifications> notification = notificationsService.getNotificationById(id);
        return notification.map(value -> ResponseEntity.ok(new ApiResponse<>(true, "Notification retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(false, "Notification not found", null)));
    }

    /**
     * 根据用户id获取通知
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Notifications>>> getNotificationsByUserId(@PathVariable Integer userId) {
        List<Notifications> notifications = notificationsService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Notifications retrieved successfully", notifications));
    }

    /**
     * 新建通知
     * @param notification
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Notifications>> createNotification(@RequestBody Notifications notification) {
        Notifications savedNotification = notificationsService.saveNotification(notification);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, "Notifications created successfully", savedNotification));
    }

    /**
     * 更新通知
     * @param id
     * @param updatedNotification
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Notifications>> updateNotification(@PathVariable Integer id, @RequestBody Notifications updatedNotification) {
        Optional<Notifications> existingNotification = notificationsService.getNotificationById(id);
        if (existingNotification.isPresent()) {
            updatedNotification.setNotificationId(id);
            Notifications savedNotification = notificationsService.saveNotification(updatedNotification);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notification updated successfully", savedNotification));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Notification not found", null));
        }
    }

    /**
     * 删除通知
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNotification(@PathVariable Integer id) {
        Optional<Notifications> existingNotification = notificationsService.getNotificationById(id);
        if (existingNotification.isPresent()) {
            notificationsService.deleteNotification(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notification deleted successfully", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Notification not found", null));
        }
    }


    /**
     * 通知分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<Notifications>>> getNotificationsByUserIdPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Notifications> notifications = notificationsService.getAllNotificationsByPage(pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Paginated notifications retrieved successfully", notifications));
    }
}

