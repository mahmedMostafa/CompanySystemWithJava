package com.salama.company.Multinational.Company.mappers;


import com.salama.company.Multinational.Company.dtos.EmployeeDto;
import com.salama.company.Multinational.Company.entities.Employee;
import com.salama.company.Multinational.Company.entities.Skill;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeDtoMapper implements Function<Employee, EmployeeDto> {
    
    @Override
    public EmployeeDto apply(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getAge(),
                employee.getSalary(),
                employee.getEmail(),
                employee.getSkills().stream().map(Skill::getName).collect(Collectors.toList()),
                employee.getDepartment(),
                employee.getManager() != null ? employee.getManager().getId() : null,
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }
}
