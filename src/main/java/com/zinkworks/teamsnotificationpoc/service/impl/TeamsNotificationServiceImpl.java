package com.zinkworks.teamsnotificationpoc.service.impl;

import com.zinkworks.teamsnotificationpoc.constants.ColorCode;
import com.zinkworks.teamsnotificationpoc.model.*;
import com.zinkworks.teamsnotificationpoc.service.TeamsNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TeamsNotificationServiceImpl implements TeamsNotificationService {

    private String colorCode;
    private String channel;
    @Override
    public void createWebHook(TeamsNotificationRequest teamsNotificationRequest) {

        log.info("Request: {}", teamsNotificationRequest);

        List<Fact> facts = teamsNotificationRequest.getSections().get(0).getFacts();
        List<Section> sections = new ArrayList<>();
        Section section = Section.builder()
                .activityTitle(teamsNotificationRequest.getSections().get(0).getActivityTitle())
                .activitySubtitle(teamsNotificationRequest.getSections().get(0).getActivitySubtitle())
                .facts(facts)
                .markdown(true)
                .build();
        sections.add(section);

        switch (teamsNotificationRequest.getLevel()) {
            case ERROR -> colorCode = ColorCode.ERROR;
            case WARN -> colorCode = ColorCode.WARN;
            case OPERATIONAL -> colorCode = ColorCode.OPERATIONAL;
        }

        switch (teamsNotificationRequest.getChannel()) {
            case GENERAL -> channel = Channel.general;
            case METRIC -> channel = Channel.metric;
        }

        Notification notification = Notification.builder()
                .type(teamsNotificationRequest.getType())
                .color(colorCode)
                .notificationTitle(teamsNotificationRequest.getTitle())
                .notificationSummary(teamsNotificationRequest.getSummary()).
                sections(sections)
                .build();

        log.info("Send Webhook notification: {}", notification);

        try {
            sendWebHook(notification, channel);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendWebHook(Notification notification, String channel) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> map = new HashMap<>();
        map.put("@type", notification.getType());
        map.put("themeColor", notification.getColor());
        map.put("title", notification.getNotificationTitle());
        map.put("summary", notification.getNotificationSummary());
        map.put("sections", notification.getSections());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(channel, entity, String.class);

        log.info("Send Webhook notification: {}, response: {}", notification, response);
    }
}