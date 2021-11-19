package org.diamond_badge.footprint.model.social;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverProfile {
	private String resultcode;
	private String message;
	private Response response;

	@Getter
	@Setter
	@ToString
	public static class Response {
		private String id;
		private String email;

	}
}
