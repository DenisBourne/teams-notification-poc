package com.zinkworks.teamsnotificationpoc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zinkworks.teamsnotificationpoc.constants.Channel;
import com.zinkworks.teamsnotificationpoc.constants.Level;
import com.zinkworks.teamsnotificationpoc.constants.NotificationConstants;
import com.zinkworks.teamsnotificationpoc.model.Fact;
import com.zinkworks.teamsnotificationpoc.model.Section;
import com.zinkworks.teamsnotificationpoc.model.TeamsNotificationRequest;
import com.zinkworks.teamsnotificationpoc.util.TestHelper;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class TeamsControllerTest {
	@Autowired private MockMvc mockMvc;
	private TeamsNotificationRequest request;
	private TeamsNotificationRequest requestNoChannel;
	private TeamsNotificationRequest requestNoSummary;
	private TeamsNotificationRequest requestNoSections;
	private final TestHelper testHelper = new TestHelper();

	@BeforeAll
	void setUp() throws Exception {
		List<Fact> facts = new ArrayList<>();
		facts.add(new Fact("Test Assign", "John"));
		List<Section> sections = new ArrayList<>();
		sections.add(new Section("Activity title", "Subtitle", facts, true));
		request =
				TeamsNotificationRequest.builder()
						.level(Level.WARN)
						.channel(Channel.GENERAL)
						.type(NotificationConstants.MESSAGE_TYPE)
						.title("Test Message")
						.summary("Test Summary")
						.sections(sections)
						.build();

		requestNoChannel =
				TeamsNotificationRequest.builder()
						.level(Level.WARN)
						.type(NotificationConstants.MESSAGE_TYPE)
						.title("Test Message")
						.summary("Test Summary")
						.sections(sections)
						.build();

		requestNoSummary =
				TeamsNotificationRequest.builder()
						.level(Level.WARN)
						.channel(Channel.GENERAL)
						.type(NotificationConstants.MESSAGE_TYPE)
						.title("Test Message")
						.sections(sections)
						.build();

		requestNoSections =
				TeamsNotificationRequest.builder()
						.level(Level.WARN)
						.channel(Channel.GENERAL)
						.type(NotificationConstants.MESSAGE_TYPE)
						.title("Test Message")
						.summary("Test Summary")
						.build();
	}

	@Test
	void sendNotificationTest() throws Exception {

		// Correct full Request
		MvcResult result = testHelper.postNotification(mockMvc, request);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		// No Channel applied
		result = testHelper.postNotification(mockMvc, requestNoChannel);
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		// No Summary applied
		result = testHelper.postNotification(mockMvc, requestNoSummary);
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		// No Sections applied
		result = testHelper.postNotification(mockMvc, requestNoSections);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
}
