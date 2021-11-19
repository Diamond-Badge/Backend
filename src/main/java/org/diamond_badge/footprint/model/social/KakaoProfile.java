package org.diamond_badge.footprint.model.social;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfile {
	private Long id;
	private Kakao_properties kakao_properties;
	private Kakao_account kakao_account;

	@Getter
	@Setter
	@ToString
	public static class Kakao_properties {
		private String nickname;
		private String profile_image;
	}

	@Getter
	@Setter
	@ToString
	public static class Kakao_account {
		private String email;
		private String nickname;
		private String profile_image;
	}
}
