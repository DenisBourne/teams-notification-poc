package com.zinkworks.teamsnotificationpoc.model;

import java.io.Serial;
import java.io.Serializable;
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
public class Fact implements Serializable {
	@Serial private static final long serialVersionUID = 1L;
	private String name;
	private String value;
}
