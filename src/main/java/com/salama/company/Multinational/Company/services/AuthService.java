package com.salama.company.Multinational.Company.services;


import com.salama.company.Multinational.Company.config.JwtService;
import com.salama.company.Multinational.Company.dtos.AuthenticationResponse;
import com.salama.company.Multinational.Company.dtos.LoginRequest;
import com.salama.company.Multinational.Company.dtos.RegisterRequest;
import com.salama.company.Multinational.Company.entities.Employee;
import com.salama.company.Multinational.Company.errors.exceptions.EmailAlreadyTakenException;
import com.salama.company.Multinational.Company.errors.exceptions.EmployeeNotFoundException;
import com.salama.company.Multinational.Company.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final EmployeesRepository employeesRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(EmployeesRepository employeesRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.employeesRepository = employeesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        final Optional<Employee> employeeOptional = employeesRepository.findEmployeeByEmail(request.email());
        if (employeeOptional.isPresent()) {
            throw new EmailAlreadyTakenException();
        }

        final Employee employee = new Employee(
                request.name(),
                request.age(),
                request.salary(),
                request.email(),
                passwordEncoder.encode(request.password()),
                null,
                null
        );
        employeesRepository.save(employee);
        final String jwtToken = jwtService.generateToken(employee);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final Employee employee = employeesRepository.findEmployeeByEmail(request.email()).orElseThrow(EmployeeNotFoundException::new);
        final String jwtToken = jwtService.generateToken(employee);
        return new AuthenticationResponse(jwtToken);
    }
}
