package com.zinkworks.teamsnotificationpoc.model;

import com.zinkworks.teamsnotificationpoc.constants.Channel;
import com.zinkworks.teamsnotificationpoc.constants.Level;
import com.zinkworks.teamsnotificationpoc.constants.NotificationType;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamsNotificationRequest {
    private Level level;
    private Channel channel;
    private String type;
    private String title;
    private String summary;
    private List<Section> sections;
}