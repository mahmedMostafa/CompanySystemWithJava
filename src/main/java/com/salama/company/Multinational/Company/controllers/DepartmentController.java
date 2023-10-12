package com.salama.company.Multinational.Company.controllers;


import com.salama.company.Multinational.Company.entities.Department;
import com.salama.company.Multinational.Company.entities.base.BaseResponse;
import com.salama.company.Multinational.Company.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping
    public void saveNewDepartment(@RequestBody Department department) {
        departmentService.saveNewDepartment(department);
    }

    @PostMapping("/addEmployee")
    public void addEmployee(@RequestBody Map<String, Long> formData) {
        departmentService.addEmployeeToDepartment(
                (Long) formData.get("department_id"),
                (Long) formData.get("employee_id")
        );
    }

    @PutMapping(path = "{departmentId}")
    public void updateDepartment(@PathVariable("departmentId") Long departmentId, @RequestBody Department department) {
        departmentService.updateDepartment(departmentId, department);
    }

    @DeleteMapping(path = "{departmentId}")
    public void deleteDepartment(@PathVariable("departmentId") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
    }
}
