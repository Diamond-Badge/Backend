package org.diamond_badge.footprint.model.social;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class GoogleProfile {

	private String name;
	private String email;
	private String id;
}
