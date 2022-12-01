package com.zinkworks.teamsnotificationpoc.util;

import com.google.gson.Gson;
import com.zinkworks.teamsnotificationpoc.constants.PathConstants;
import com.zinkworks.teamsnotificationpoc.model.TeamsNotificationRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TestHelper {
	private final Gson g = new Gson();

	public MvcResult postNotification(final MockMvc mockMvc, final TeamsNotificationRequest request)
			throws Exception {
		return mockMvc
				.perform(
						MockMvcRequestBuilders.post(PathConstants.BASE_URL + PathConstants.NOTIFICATION)
								.contentType(MediaType.APPLICATION_JSON)
								.content(g.toJson(request))
								.accept(MediaType.APPLICATION_JSON))
				.andReturn();
	}
}
