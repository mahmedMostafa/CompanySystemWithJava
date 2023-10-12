package com.salama.company.Multinational.Company.repositories;

import com.salama.company.Multinational.Company.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmployeesRepository extends JpaRepository<Employee, Long> {


    Optional<Employee> findEmployeeByEmail(String email);
}
