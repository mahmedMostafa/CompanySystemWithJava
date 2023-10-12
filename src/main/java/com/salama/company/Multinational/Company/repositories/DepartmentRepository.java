package com.salama.company.Multinational.Company.repositories;

import com.salama.company.Multinational.Company.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findDepartmentByName(String name);

}
