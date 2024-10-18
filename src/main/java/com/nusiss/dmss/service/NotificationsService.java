package com.nusiss.dmss.service;

import com.nusiss.dmss.entity.Notifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NotificationsService {
    List<Notifications> getAllNotifications();
    Optional<Notifications> getNotificationById(Integer id);
    List<Notifications> getNotificationsByUserId(Integer userId);
    Notifications saveNotification(Notifications notification);
    void deleteNotification(Integer id);
    Page<Notifications> getAllNotificationsByPage(Pageable pageable);
}
