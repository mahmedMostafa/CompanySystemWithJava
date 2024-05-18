package com.salama.company.Multinational.Company.services;


import com.salama.company.Multinational.Company.dtos.SkillRequest;
import com.salama.company.Multinational.Company.entities.Skill;
import com.salama.company.Multinational.Company.entities.enums.SkillLevel;
import com.salama.company.Multinational.Company.repositories.SkillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillsService {

	private final SkillRepository skillRepository;

	@Autowired
	public SkillsService(SkillRepository skillRepository) {
		this.skillRepository = skillRepository;
	}

	public List<Skill> getAllSkills() {
		return skillRepository.findAll();
	}

	public void saveNewSkill(SkillRequest skill) {
		Optional<Skill> optionalSkill = skillRepository.findSkillByName(skill.name());
		if (optionalSkill.isPresent()) {
			throw new IllegalStateException("This skill already exists");
		}
		skillRepository.save(new Skill(skill.name(), SkillLevel.valueOf(skill.skillLevel())));
	}

	public void removeSkill(Long skillId) {
		Optional<Skill> optionalSkill = skillRepository.findById(skillId);
		if (optionalSkill.isEmpty()) {
			throw new IllegalStateException("This skill doesn't exist");
		}
		skillRepository.deleteById(skillId);
	}
}
