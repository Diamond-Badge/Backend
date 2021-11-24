package org.diamond_badge.footprint.jpa.entity;

public enum EmotionType {
	DEFAULT(Values.DEFAULT),
	EXCITED(Values.EXCITED),
	HAPPY(Values.HAPPY),
	SAD(Values.SAD),
	ANGRY(Values.ANGRY),
	DEPRESSED(Values.DEPRESSED);

	private String value;

	EmotionType(String val) {
		if (!this.name().equals(val)) {
			throw new IllegalArgumentException("Incorrect use of EmotionType");
		}
	}

	public static class Values {
		public static final String DEFAULT = "DEFAULT";
		public static final String EXCITED = "EXCITED";
		public static final String HAPPY = "HAPPY";
		public static final String SAD = "SAD";
		public static final String ANGRY = "ANGRY";
		public static final String DEPRESSED = "DEPRESSED";
	}

}
