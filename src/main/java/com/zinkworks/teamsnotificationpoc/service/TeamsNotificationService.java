package com.zinkworks.teamsnotificationpoc.service;

import com.zinkworks.teamsnotificationpoc.model.Notification;

import java.net.URISyntaxException;

public interface TeamsNotificationService {
    void createWebHook();
    void sendWebHook(Notification notification) throws URISyntaxException;
}
