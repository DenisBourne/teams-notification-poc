package com.zinkworks.teamsnotificationpoc.model;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notification implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	@NotNull private String type;
	@NotNull private String channel;
	private String color;
	@NotNull private String notificationTitle;
	@NotNull private String notificationSummary;
	private List<Section> sections;
}
