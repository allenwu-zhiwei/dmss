package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.NotificationRepository;
import com.nusiss.dmss.entity.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notifications> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notifications> getNotificationById(Integer id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notifications> getNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public Notifications saveNotification(Notifications notification) {
        Notifications savedNotification = notificationRepository.save(notification);
        savedNotification.triggerNotification();
        return savedNotification;
    }

    @Override
    public void deleteNotification(Integer id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Page<Notifications> getAllNotificationsByPage(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }
}
