package com.zinkworks.teamsnotificationpoc.controller;

import com.zinkworks.teamsnotificationpoc.constants.PathConstants;
import com.zinkworks.teamsnotificationpoc.exception.ExceptionDetails;
import com.zinkworks.teamsnotificationpoc.model.TeamsNotificationRequest;
import com.zinkworks.teamsnotificationpoc.service.TeamsNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.BASE_URL)
@Slf4j
public class TeamsController {
	@Autowired private TeamsNotificationService service;

	@Operation(
			summary = "Send notification message to Teams channel",
			description = "Send notification message to Teams channel")
	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "200", description = "Notification sent successfully"),
				@ApiResponse(
						responseCode = "400",
						description = "Bad Request",
						content =
								@Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE,
										schema = @Schema(implementation = ExceptionDetails.class))),
				@ApiResponse(
						responseCode = "500",
						description = "Service Error",
						content =
								@Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE,
										schema = @Schema(implementation = ExceptionDetails.class)))
			})
	@PostMapping(path = PathConstants.NOTIFICATION)
	public ResponseEntity<TeamsNotificationRequest> sendNotification(
			@RequestBody @Valid TeamsNotificationRequest request) throws Exception {
		this.service.sendWebHook(request);
		return new ResponseEntity<>(request, HttpStatus.OK);
	}
}
