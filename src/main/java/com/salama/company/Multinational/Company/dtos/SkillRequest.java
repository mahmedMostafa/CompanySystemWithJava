package com.salama.company.Multinational.Company.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.salama.company.Multinational.Company.annotations.ValidSkillLevel;
import jakarta.validation.constraints.NotBlank;

public record SkillRequest(
		@NotBlank
		String name,
		@ValidSkillLevel
		@JsonProperty("skill_level")
		String skillLevel
) {
}
