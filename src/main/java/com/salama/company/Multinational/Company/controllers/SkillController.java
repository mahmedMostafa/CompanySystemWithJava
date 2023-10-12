package com.salama.company.Multinational.Company.controllers;


import com.salama.company.Multinational.Company.entities.Skill;
import com.salama.company.Multinational.Company.entities.base.BaseResponse;
import com.salama.company.Multinational.Company.services.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public void saveNewSkill(@RequestBody Skill skill) {
        skillsService.saveNewSkill(skill);
    }

    @DeleteMapping(path = "{skillId}")
    public void deleteSkill(@PathVariable("skillId") Long skillId) {
        skillsService.removeSkill(skillId);
    }
}
