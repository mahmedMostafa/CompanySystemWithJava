package com.salama.company.Multinational.Company.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.salama.company.Multinational.Company.entities.Department;
import com.salama.company.Multinational.Company.entities.Employee;
import com.salama.company.Multinational.Company.entities.Passport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@JsonPropertyOrder({"id", "name", "age", "salary", "email", "manager_id", "department", "skills"})
public record EmployeeDto(
        Long id,
        @NotBlank
        String name,
        @NotNull
        int age,
        @NotNull
        float salary,
        @NotBlank
        String email,
        List<String> skills,
        Department department,
        @JsonProperty("manager_id")
        Long managerId,
        @JsonProperty("created_at")
//        @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
        LocalDateTime createdAt,
        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
}

