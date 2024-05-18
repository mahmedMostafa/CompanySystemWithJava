package com.salama.company.Multinational.Company.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.salama.company.Multinational.Company.annotations.validators.SkillLevelValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = SkillLevelValidator.class)
public @interface ValidSkillLevel {

	public String message() default "Not a valid skill level, this is an enum value, so please pass the exact name";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};


}
