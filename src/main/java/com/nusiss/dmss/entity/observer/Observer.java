package com.nusiss.dmss.entity.observer;

import com.nusiss.dmss.entity.Notifications;

public interface Observer {
    void update(Notifications notification);  // update when notification is created
}
