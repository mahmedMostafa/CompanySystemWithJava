package com.salama.company.Multinational.Company.dtos;

import java.util.List;

public record EmployeeRequestBody(
        String name,
        Integer age,
        Float salary,
        String email,
        List<String> skills,
        Long departmentId,
        Long managerId
) {
}
