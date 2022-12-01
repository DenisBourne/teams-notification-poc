package com.zinkworks.teamsnotificationpoc.model;

import com.zinkworks.teamsnotificationpoc.constants.Channel;
import com.zinkworks.teamsnotificationpoc.constants.Level;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamsNotificationRequest {
	@Nullable private Level level;
	@NotNull private Channel channel;
	@NotNull private String type;
	private String title;
	@NotNull private String summary;
	private List<Section> sections;
}
