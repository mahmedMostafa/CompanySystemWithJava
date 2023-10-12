package com.salama.company.Multinational.Company.services;


import com.salama.company.Multinational.Company.entities.Skill;
import com.salama.company.Multinational.Company.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void saveNewSkill(Skill skill) {
        Optional<Skill> optionalSkill = skillRepository.findSkillByName(skill.getName());
        if (optionalSkill.isPresent()) {
            throw new IllegalStateException("This skill already exists");
        }
        skillRepository.save(skill);
    }

    public void removeSkill(Long skillId) {
        Optional<Skill> optionalSkill = skillRepository.findById(skillId);
        if (optionalSkill.isEmpty()) {
            throw new IllegalStateException("This skill doesn't exist");
        }
        skillRepository.deleteById(skillId);
    }
}
