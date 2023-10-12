package com.salama.company.Multinational.Company.repositories;


import com.salama.company.Multinational.Company.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findSkillByName(String name);
}
