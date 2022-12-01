package com.zinkworks.teamsnotificationpoc.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Section implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	private String activityTitle;
	private String activitySubtitle;
	private List<Fact> facts;
	private boolean markdown;
}
