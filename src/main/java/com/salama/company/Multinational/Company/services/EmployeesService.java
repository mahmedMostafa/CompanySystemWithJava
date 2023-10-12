package com.salama.company.Multinational.Company.services;


import com.salama.company.Multinational.Company.dtos.EmployeeDto;
import com.salama.company.Multinational.Company.dtos.EmployeeRequestBody;
import com.salama.company.Multinational.Company.entities.Address;
import com.salama.company.Multinational.Company.entities.Employee;
import com.salama.company.Multinational.Company.entities.Passport;
import com.salama.company.Multinational.Company.entities.Skill;
import com.salama.company.Multinational.Company.errors.exceptions.EmailAlreadyTakenException;
import com.salama.company.Multinational.Company.errors.exceptions.EmployeeNotFoundException;
import com.salama.company.Multinational.Company.mappers.EmployeeDtoMapper;
import com.salama.company.Multinational.Company.repositories.EmployeesRepository;
import com.salama.company.Multinational.Company.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeesService {

    private final EmployeeDtoMapper employeeDtoMapper;
    private final EmployeesRepository employeesRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public EmployeesService(EmployeeDtoMapper employeeDtoMapper, EmployeesRepository employeesRepository, SkillRepository skillRepository) {
        this.employeeDtoMapper = employeeDtoMapper;
        this.employeesRepository = employeesRepository;
        this.skillRepository = skillRepository;
    }

    public List<EmployeeDto> getAllEmployees() {
        /*
          Find all uses JPQL under the hood, which means if there's any relation with other tables
          then it will make multiple queries instead of a joined one query, so even if we annotate the field with
          @Fetch(FetchMode.JOIN), it will not matter. Refer to this https://stackoverflow.com/a/35426751
         * maybe the solution is to write the query ourselves? look more into this
         */
        return employeesRepository.findAll()
                .stream()
                .map(employeeDtoMapper)
                .collect(Collectors.toList());
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeesRepository.findById(id);
    }

    public void saveNewEmployee(EmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = employeesRepository.findEmployeeByEmail(employeeDto.email());
        if (employeeOptional.isPresent()) {
            throw new EmailAlreadyTakenException();
        }
        Employee employee = new Employee(
                employeeDto.name(),
                employeeDto.age(),
                employeeDto.salary(),
                employeeDto.email(),
                null,
                null,
                employeeDto.department()
        );
        employeesRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        boolean exists = employeesRepository.existsById(employeeId);
        if (!exists) {
            throw new EmployeeNotFoundException();
        }
        employeesRepository.deleteById(employeeId);
    }

    @Transactional
    public void updateEmployee(Long employeeId, String name, String email) {
        Employee employee = employeesRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
        employee.setName(name);

        if (email != null && !email.isEmpty() && !email.equals(employee.getEmail())) {
            Optional<Employee> employeeOptional = employeesRepository.findEmployeeByEmail(email);
            if (employeeOptional.isPresent()) {
                throw new EmailAlreadyTakenException();
            }
        }
        employee.setEmail(email);
    }

    @Transactional
    public void updateEmployeeFields(Long employeeId, EmployeeRequestBody employeeRequestBody) {
        Employee employee = employeesRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
        if (employeeRequestBody.name() != null) {
            employee.setName(employeeRequestBody.name());
        }

        if (employeeRequestBody.email() != null && !employeeRequestBody.email().isEmpty() && !employeeRequestBody.email().equals(employee.getEmail())) {
            Optional<Employee> employeeOptional = employeesRepository.findEmployeeByEmail(employeeRequestBody.email());
            if (employeeOptional.isPresent()) {
                throw new EmailAlreadyTakenException();
            }
            employee.setEmail(employeeRequestBody.email());
        }

        if (employeeRequestBody.age() != null) {
            employee.setAge(employeeRequestBody.age());
        }
        if (employeeRequestBody.salary() != null) {
            employee.setSalary(employeeRequestBody.salary());
        }
        if (employeeRequestBody.managerId() != null) {
            Employee manager = employeesRepository.findById(employeeRequestBody.managerId())
                    .orElseThrow(EmployeeNotFoundException::new);
            employee.setManager(manager);
        }
    }

    public void insertPassport(Long employeeId, Passport passport) {
        Employee employee = employeesRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
        employee.setPassport(passport);
        employeesRepository.save(employee);
    }

    @Transactional
    public void addSkillToEmployee(Long employeeId, Skill skill) {
        Employee employee = employeesRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);

        if (skill == null || skill.getName() == null) {
            throw new IllegalStateException("Please insert valid data");
        }

        //if the skill exists add it, if not then create a new one and add it
        Optional<Skill> skillOptional = skillRepository.findSkillByName(skill.getName());
        Skill savedSkill = skillOptional.orElseGet(() -> new Skill(skill.getName(), skill.getSkillLevel()));

        //update skills
        employee.getSkills().add(savedSkill);
    }

    @Transactional
    public void addAddressToEmployee(Long employeeId, Address address) {
        Employee employee = employeesRepository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);

        employee.getAddresses().add(address);
    }
}
