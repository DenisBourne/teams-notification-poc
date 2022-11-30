package com.zinkworks.teamsnotificationpoc.controller;

import com.zinkworks.teamsnotificationpoc.constants.PathConstants;
import com.zinkworks.teamsnotificationpoc.model.TeamsNotificationRequest;
import com.zinkworks.teamsnotificationpoc.service.TeamsNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.BASE_URL)
@Slf4j
public class TeamsController {
    @Autowired
    private TeamsNotificationService service;

    @Operation(summary = "Send notification message to Teams channel", description = "Send notification message to Teams channel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification sent successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Service Error")
    })
    @PostMapping(path = PathConstants.NOTIFICATION)
    public ResponseEntity<TeamsNotificationRequest> sendNotification(@RequestBody @Valid TeamsNotificationRequest request) {
        log.info("Request Controller: {}", request);
        this.service.createWebHook(request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}