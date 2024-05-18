package com.salama.company.Multinational.Company.annotations.validators;

import com.salama.company.Multinational.Company.annotations.ValidSkillLevel;
import com.salama.company.Multinational.Company.entities.enums.SkillLevel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SkillLevelValidator implements ConstraintValidator<ValidSkillLevel, String> {

	@Override
	public boolean isValid(String passedValue, ConstraintValidatorContext constraintValidatorContext) {
		return contains(passedValue);
	}

	public static boolean contains(String skill) {

		for (SkillLevel skillLevel : SkillLevel.values()) {
			if (skillLevel.name().equals(skill)) {
				return true;
			}
		}

		return false;
	}
}
