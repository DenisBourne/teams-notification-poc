package com.zinkworks.teamsnotificationpoc.model;

import com.zinkworks.teamsnotificationpoc.constants.ColorCode;
import com.zinkworks.teamsnotificationpoc.constants.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notification implements Serializable {
    private NotificationType type;
    private String color;
    private String notificationTitle;
    private String notificationSummary;
    private Section section;
    private boolean markdown;
}