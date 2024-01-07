package com.salama.company.Multinational.Company.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salama.company.Multinational.Company.entities.enums.SkillLevel;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    private String name;

    @Enumerated(EnumType.STRING) //to return the string value, not the position
    @Column(name = "skill_level")
    @JsonProperty("skill_level")
    private SkillLevel skillLevel;

    //this is the inverse end of the relationship
    @ManyToMany(mappedBy = "skills")
    @JsonIgnore
    private Set<Employee> employees = new HashSet<>();

    public Skill() {
    }

    public Skill(String name, SkillLevel skillLevel) {
        this.name = name;
        this.skillLevel = skillLevel;
    }

    public Skill(Long id, String name, SkillLevel skillLevel) {
        this.skillId = id;
        this.name = name;
        this.skillLevel = skillLevel;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    //because the employee is the owner of this relationship
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getSkills().add(this);
    }

    //because the employee is the owner of this relationship
    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getSkills().remove(this);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + skillId +
                ", name='" + name + '\'' +
                ", skillLevel=" + skillLevel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(skillId, skill.skillId) && Objects.equals(name, skill.name) && skillLevel == skill.skillLevel && Objects.equals(employees, skill.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, name, skillLevel, employees);
    }
}
