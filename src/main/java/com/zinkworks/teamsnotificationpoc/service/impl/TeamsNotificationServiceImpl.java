package com.zinkworks.teamsnotificationpoc.service.impl;

import com.zinkworks.teamsnotificationpoc.constants.ColorCode;
import com.zinkworks.teamsnotificationpoc.constants.NotificationType;
import com.zinkworks.teamsnotificationpoc.model.Notification;
import com.zinkworks.teamsnotificationpoc.service.TeamsNotificationService;
import org.springframework.beans.factory.annotation.Value;

public class TeamsNotificationServiceImpl implements TeamsNotificationService {

    @Value("${microsoft.teams.url}")
    private String url;

    @Override
    public void createWebHook() {
        Notification.builder()
                .type(NotificationType.MESSAGE_CARD)
                .color(ColorCode.GREEN)
                .notificationTitle("Spring Notification Test")
                .notificationSummary("this is the message content")
                .build();
    }

    @Override
    public void sendWebHook() {

    }
}