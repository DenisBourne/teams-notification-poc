package com.zinkworks.teamsnotificationpoc.service.impl;

import com.zinkworks.teamsnotificationpoc.constants.ColorCode;
import com.zinkworks.teamsnotificationpoc.constants.NotificationType;
import com.zinkworks.teamsnotificationpoc.model.Fact;
import com.zinkworks.teamsnotificationpoc.model.Notification;
import com.zinkworks.teamsnotificationpoc.model.Section;
import com.zinkworks.teamsnotificationpoc.service.TeamsNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TeamsNotificationServiceImpl implements TeamsNotificationService {

    @Value("${microsoft.teams.url}")
    private String url;

    @Override
    public void createWebHook() {
        List<Fact> facts = new ArrayList<>();
        Fact fact = new Fact("Assigned To", "John Doe");
        Fact fact2 = new Fact("Date", java.time.LocalDateTime.now().toString());
        Fact fact3 = new Fact("Status", "Down");

        facts.add(fact);
        facts.add(fact2);
        facts.add(fact3);

        List<Section> sections = new ArrayList<>();
        Section section = Section.builder()
                .activityTitle("Activity Title")
                .activitySubtitle("Notification Service")
                .facts(facts)
                .markdown(true)
                .build();
        sections.add(section);

        Notification notification = Notification.builder()
                .type(NotificationType.MESSAGE_CARD)
                .color(ColorCode.RED)
                .notificationTitle("Spring Notification Test")
                .notificationSummary("This is the message content").
                sections(sections)
                .build();

        log.info("Send Webhook notification: {}", notification);

        try {
            sendWebHook(notification);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendWebHook(Notification notification) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
        map.put("@type", notification.getType());
        map.put("themeColor", notification.getColor());
        map.put("title", notification.getNotificationTitle());
        map.put("summary", notification.getNotificationSummary());
        map.put("sections", notification.getSections());

        URI uri = new URI("https://adeuennetworks.webhook.office.com/webhookb2/8e0f35ed-cb72-4d62-a84a-e1d0775c91c9@2967ec51-991d-4291-b934-24ba41c27a93/IncomingWebhook/d7f930752b754b458a62b7e27a0fe2d2/9e955331-7d38-462b-8bd8-c8ee6fd82214");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);

        log.info("Send Webhook notification: {}, response: {}", notification, response);
    }
}
