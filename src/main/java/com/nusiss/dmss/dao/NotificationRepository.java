package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Integer> {
    List<Notifications> findByUser_UserId(Integer userId);
}
