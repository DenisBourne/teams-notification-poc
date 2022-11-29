package com.zinkworks.teamsnotificationpoc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Section {
    private String activityTitle;
    private String activitySubtitle;
    private List<Fact> facts;
    private boolean markdown;
}
