package com.salama.company.Multinational.Company.services;


import com.salama.company.Multinational.Company.entities.Department;
import com.salama.company.Multinational.Company.entities.Employee;
import com.salama.company.Multinational.Company.errors.exceptions.EmployeeNotFoundException;
import com.salama.company.Multinational.Company.repositories.DepartmentRepository;
import com.salama.company.Multinational.Company.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeesRepository employeesRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeesRepository employeesRepository) {
        this.departmentRepository = departmentRepository;
        this.employeesRepository = employeesRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public void saveNewDepartment(Department department) {
        Optional<Department> departmentOptional = departmentRepository.findDepartmentByName(department.getName());
        if (departmentOptional.isPresent()) {
            throw new IllegalStateException("There is already a departmentId with this name");
        }
        departmentRepository.save(department);
    }

    public void updateDepartment(Long departmentId, Department department) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isEmpty()) {
            throw new IllegalStateException("This departmentId doesn't exist");
        }
        department.setId(departmentId);
        departmentRepository.save(department);
    }

    public void deleteDepartment(Long departmentId) {
        boolean doesExists = departmentRepository.existsById(departmentId);
        if (!doesExists) {
            throw new IllegalStateException("This departmentId doesn't exist");
        }
        departmentRepository.deleteById(departmentId);
    }

    public void addEmployeeToDepartment(Long departmentId, Long employeeId) {
        Optional<Employee> employeeOptional = employeesRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isEmpty()) {
            throw new IllegalStateException("Department doesn't exist");
        }
        //update departmentId
        departmentOptional.get().addEmployee(employeeOptional.get());
        departmentRepository.save(departmentOptional.get());
    }
}
