package com.zinkworks.teamsnotificationpoc.service;

import com.zinkworks.teamsnotificationpoc.model.TeamsNotificationRequest;

public interface TeamsNotificationService {
	void sendWebHook(TeamsNotificationRequest teamsNotificationRequest) throws Exception;
}
