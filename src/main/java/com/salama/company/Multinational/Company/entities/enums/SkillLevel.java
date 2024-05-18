package com.salama.company.Multinational.Company.entities.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum SkillLevel {
	BASIC,
	INTERMEDIATE,
	ADVANCED,
	EXPERT;

	public static String availableSkillLevels() {
		return Arrays.stream(SkillLevel.values())
				.map(String::valueOf)
				.collect(Collectors.joining("-"));
	}
}
