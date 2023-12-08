package com.salama.company.Multinational.Company.services;

import com.salama.company.Multinational.Company.entities.Department;
import com.salama.company.Multinational.Company.repositories.DepartmentRepository;
import com.salama.company.Multinational.Company.repositories.EmployeesRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    private DepartmentRepository departmentRepository = mock();

    private EmployeesRepository employeesRepository = mock();


    private DepartmentService departmentService = new DepartmentService(departmentRepository, employeesRepository);

    @Test
    void givenNewDepartment_whenSavingIt_thenRepositoryIsCalled() {
        //Given
        String departmentName = "computers";
        when(departmentRepository.findDepartmentByName("computers")).thenReturn(Optional.empty());

        //When
        departmentService.saveNewDepartment(new Department(10L, "computers", anySet()));

        //Then
        verify(departmentRepository, times(1)).findDepartmentByName("computers");
    }


    @Test
    void givenExistedDepartment_whenSavingIt_anExceptionIsCalled() {
        //Given
        String departmentName = "computers";
        when(departmentRepository.findDepartmentByName("computers")).thenReturn(Optional.of(new Department()));

        //Then
        assertThrows(IllegalStateException.class, () -> departmentService.saveNewDepartment(new Department(10L, "computers", anySet())));
    }

}