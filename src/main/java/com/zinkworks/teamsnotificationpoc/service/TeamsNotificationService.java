package com.zinkworks.teamsnotificationpoc.service;

import com.zinkworks.teamsnotificationpoc.model.Notification;
import com.zinkworks.teamsnotificationpoc.model.TeamsNotificationRequest;

import java.net.URISyntaxException;

public interface TeamsNotificationService {
    void createWebHook(TeamsNotificationRequest teamsNotificationRequest);
    void sendWebHook(Notification notification, String channel) throws URISyntaxException;
}