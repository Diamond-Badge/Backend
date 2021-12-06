package org.diamond_badge.footprint.jpa.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StickerType {
	DEFAULT(Values.DEFAULT),
	EXCITED(Values.EXCITED),
	HAPPY(Values.HAPPY),
	SAD(Values.SAD),
	ANGRY(Values.ANGRY),
	DEPRESSED(Values.DEPRESSED),
	HEART(Values.HEART),
	NOTEBOOK(Values.NOTEBOOK),
	BATTERY(Values.BATTERY),
	NETFLIX(Values.NETFLIX),
	SUN(Values.SUN),
	MOON(Values.MOON),
	CLOUD(Values.CLOUD),
	UMBRELLA(Values.UMBRELLA),
	SNOWMAN(Values.SNOWMAN),
	TAPE(Values.TAPE),
	LIGHTNING(Values.LIGHTNING),
	MANGMANG(Values.MANGMANG),
	KUNGKUNG(Values.KUNGKUNG);

	StickerType(String val) {
		if (!this.name().equals(val)) {
			throw new IllegalArgumentException("Incorrect use of StickerType");
		}
	}

	public static class Values {
		public static final String DEFAULT = "DEFAULT";
		public static final String EXCITED = "EXCITED";
		public static final String HAPPY = "HAPPY";
		public static final String SAD = "SAD";
		public static final String ANGRY = "ANGRY";
		public static final String DEPRESSED = "DEPRESSED";
		public static final String HEART = "HEART";
		public static final String NOTEBOOK = "NOTEBOOK";
		public static final String BATTERY = "BATTERY";
		public static final String NETFLIX = "NETFLIX";
		public static final String SUN = "SUN";
		public static final String MOON = "MOON";
		public static final String CLOUD = "CLOUD";
		public static final String UMBRELLA = "UMBRELLA";
		public static final String SNOWMAN = "SNOWMAN";
		public static final String TAPE = "TAPE";
		public static final String LIGHTNING = "LIGHTNING";
		public static final String MANGMANG = "MANGMANG";
		public static final String KUNGKUNG = "KUNGKUNG";
	}
}
