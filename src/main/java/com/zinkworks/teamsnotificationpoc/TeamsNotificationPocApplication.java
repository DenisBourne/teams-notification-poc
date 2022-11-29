package com.zinkworks.teamsnotificationpoc;

import com.zinkworks.teamsnotificationpoc.service.impl.TeamsNotificationServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeamsNotificationPocApplication {

    public static void main(String[] args) {

        SpringApplication.run(TeamsNotificationPocApplication.class, args);

        TeamsNotificationServiceImpl notificationService = new TeamsNotificationServiceImpl();
        notificationService.createWebHook();
    }
}


