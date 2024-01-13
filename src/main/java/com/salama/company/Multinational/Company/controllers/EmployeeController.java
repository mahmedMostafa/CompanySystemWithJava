package com.salama.company.Multinational.Company.controllers;


import com.salama.company.Multinational.Company.dtos.EmployeeDto;
import com.salama.company.Multinational.Company.dtos.EmployeeRequestBody;
import com.salama.company.Multinational.Company.dtos.SkillRequest;
import com.salama.company.Multinational.Company.entities.Address;
import com.salama.company.Multinational.Company.entities.Passport;
import com.salama.company.Multinational.Company.services.EmployeesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@Validated
public class EmployeeController {


    private final EmployeesService employeesService;

    @Autowired
    public EmployeeController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeesService.getAllEmployees();
    }

    @PostMapping
    public ResponseEntity<?> saveNewEmployee(@RequestBody @Valid EmployeeDto employee) {
        return new ResponseEntity<>(employeesService.saveNewEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping(path = "{employeeId}")
    public void updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @RequestParam(required = false) @Valid String name,
            @RequestParam(required = false) @Valid String email
    ) {
        employeesService.updateEmployee(employeeId, name, email);
    }

    @PutMapping(path = "/update-fields/{employeeId}")
    public void updateEmployeeFields(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody EmployeeRequestBody employeeRequestBody
    ) {
        employeesService.updateEmployeeFields(employeeId, employeeRequestBody);
    }

    @DeleteMapping(path = "{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeesService.deleteEmployee(employeeId);
    }

    @PutMapping(value = "/insert-passport/{employeeId}")
    public void insertPassport(
            @PathVariable("employeeId") Long employeeId,
            @Valid @RequestBody Passport passport
    ) {
        employeesService.insertPassport(employeeId, passport);
    }

    @PutMapping(value = "/add-skill/{employeeId}")
    public void addSkill(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody @Valid SkillRequest skill
    ) {
        employeesService.addSkillToEmployee(employeeId, skill);
    }

    @PutMapping(value = "/add-address/{employeeId}")
    public void addAddress(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody Address address
    ) {
        employeesService.addAddressToEmployee(employeeId, address);
    }
}
