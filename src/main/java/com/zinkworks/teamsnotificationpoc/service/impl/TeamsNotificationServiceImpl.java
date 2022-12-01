package com.zinkworks.teamsnotificationpoc.service.impl;

import com.zinkworks.teamsnotificationpoc.constants.Channel;
import com.zinkworks.teamsnotificationpoc.constants.ColorCode;
import com.zinkworks.teamsnotificationpoc.constants.Level;
import com.zinkworks.teamsnotificationpoc.constants.NotificationConstants;
import com.zinkworks.teamsnotificationpoc.exception.InvalidRequestException;
import com.zinkworks.teamsnotificationpoc.model.ChannelURL;
import com.zinkworks.teamsnotificationpoc.model.Fact;
import com.zinkworks.teamsnotificationpoc.model.Notification;
import com.zinkworks.teamsnotificationpoc.model.Section;
import com.zinkworks.teamsnotificationpoc.model.TeamsNotificationRequest;
import com.zinkworks.teamsnotificationpoc.service.TeamsNotificationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TeamsNotificationServiceImpl implements TeamsNotificationService {
	@Override
	public void sendWebHook(TeamsNotificationRequest teamsNotificationRequest) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Notification notification = buildNotification(teamsNotificationRequest);

		Map<String, Object> map = getNotificationMap(notification);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		restTemplate.postForEntity(notification.getChannel(), entity, String.class);
	}

	private Notification buildNotification(TeamsNotificationRequest teamsNotificationRequest)
			throws Exception {
		log.info("Request: {}", teamsNotificationRequest);
		List<Section> sections = new ArrayList<>();

		if (teamsNotificationRequest.getType() == null
				|| teamsNotificationRequest.getChannel() == null
				|| teamsNotificationRequest.getSummary() == null) {
			throw new InvalidRequestException(
					"Request does not contain correct parameters please check and try again");
		}

		if (teamsNotificationRequest.getSections() != null) {
			sections.add(getSections(teamsNotificationRequest));
		}

		return Notification.builder()
				.type(teamsNotificationRequest.getType())
				.channel(getChannel(teamsNotificationRequest.getChannel()))
				.color(getColorCode(teamsNotificationRequest.getLevel()))
				.notificationTitle(teamsNotificationRequest.getTitle())
				.notificationSummary(teamsNotificationRequest.getSummary())
				.sections(sections)
				.build();
	}

	private Map<String, Object> getNotificationMap(Notification notification) {
		Map<String, Object> map = new HashMap<>();
		map.put(NotificationConstants.TYPE, notification.getType());
		map.put(NotificationConstants.THEME_COLOR, notification.getColor());
		map.put(NotificationConstants.TITLE, notification.getNotificationTitle());
		map.put(NotificationConstants.SUMMARY, notification.getNotificationSummary());
		map.put(NotificationConstants.SECTIONS, notification.getSections());
		return map;
	}

	private Section getSections(TeamsNotificationRequest teamsNotificationRequest) {
		List<Fact> facts = teamsNotificationRequest.getSections().get(0).getFacts();
		return Section.builder()
				.activityTitle(teamsNotificationRequest.getSections().get(0).getActivityTitle())
				.activitySubtitle(teamsNotificationRequest.getSections().get(0).getActivitySubtitle())
				.facts(facts)
				.markdown(true)
				.build();
	}

	private String getColorCode(Level colorValue) {
		if (colorValue == null) {
			return ColorCode.DEFAULT;
		} else {
			return switch (colorValue) {
				case ERROR -> ColorCode.ERROR;
				case WARN -> ColorCode.WARN;
				case OPERATIONAL -> ColorCode.OPERATIONAL;
			};
		}
	}

	private String getChannel(Channel channel) {
		return switch (channel) {
			case GENERAL -> ChannelURL.general;
			case METRIC -> ChannelURL.metric;
		};
	}
}
