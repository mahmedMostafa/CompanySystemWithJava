package com.salama.company.Multinational.Company.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salama.company.Multinational.Company.dtos.SkillRequest;
import com.salama.company.Multinational.Company.entities.Skill;
import com.salama.company.Multinational.Company.services.SkillsService;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("api/v1/skills")
public class SkillController {

    private final SkillsService skillsService;

    @Autowired
    public SkillController(SkillsService skillsService) {
        this.skillsService = skillsService;
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillsService.getAllSkills();
    }

    @PostMapping
    public void saveNewSkill(@Valid @RequestBody SkillRequest skill) {
        skillsService.saveNewSkill(skill);
    }

    @DeleteMapping(path = "{skillId}")
    public void deleteSkill(@PathVariable("skillId") Long skillId) {
        skillsService.removeSkill(skillId);
    }
}
