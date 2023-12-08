package com.salama.company.Multinational.Company.services;


import com.salama.company.Multinational.Company.entities.Employee;
import com.salama.company.Multinational.Company.mappers.EmployeeDtoMapper;
import com.salama.company.Multinational.Company.repositories.EmployeesRepository;
import com.salama.company.Multinational.Company.repositories.SkillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeesServiceTest {

    @MockBean
    private EmployeesRepository employeesRepository;

    @MockBean
    private SkillRepository skillRepository;

    @Autowired
    private EmployeeDtoMapper employeeDtoMapper;

    @Autowired
    private EmployeesService employeesServiceUnderTest;


    @Test
    void whenFindingAnEmployeeIdThatExists_thenReturnTheEmployeeDetails() {
        //Given
        Long id = 10L;
        Employee actual = new Employee();
        when(employeesRepository.findById(id)).thenReturn(Optional.of(actual));

        //When
        Optional<Employee> employeeOptional = employeesServiceUnderTest.getEmployeeById(id);

        //Assert
        assertSame(employeeOptional.get(), actual);

        //Verify
        verify(employeesRepository, times(1)).findById(id);
    }
}