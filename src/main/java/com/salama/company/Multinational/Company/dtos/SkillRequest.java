package com.salama.company.Multinational.Company.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record SkillRequest(
        @NotBlank
        String name,
        @NotBlank
        @JsonProperty("skill_level")
        String skillLevel
) {
}
