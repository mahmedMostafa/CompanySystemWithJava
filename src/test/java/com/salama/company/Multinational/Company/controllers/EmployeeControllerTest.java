package com.salama.company.Multinational.Company.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salama.company.Multinational.Company.dtos.EmployeeDto;
import com.salama.company.Multinational.Company.entities.Department;
import com.salama.company.Multinational.Company.mappers.EmployeeDtoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * NOTE: i didn't like this integration test, i think we should just unit test this controller with the help of
 * @WebMvcTest annotation instead of @SpingBootTest one to speed up the test and take the controller advice being enabled
 * by default which is not the case with @SpingBootTest. that's why i didn't add a test to handle an exception
 */

/**
 * This test uses h2 memory database to perform database queries, but it's not the most optimal approach
 * We could use TestContainers instead to simulate a production mysql database
 */
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class EmployeeControllerTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeDtoMapper employeeDtoMapper;


    @BeforeEach
    void setup() {
        jdbc.execute("INSERT INTO employees(employee_name, age, salary, email) VALUES('mohamed', 23, 22000, 'mohamedsalama@gmailc.om')");
    }

    @Test
    void givenDataAlreadyInDb_whenGettingEmployees_shouldReturn200withListOfEmployees() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].name", is("mohamed")))
                .andExpect(jsonPath("$.data[0].email", is("mohamedsalama@gmailc.om")))
                .andExpect(jsonPath("$.data[0].age", is(23)))
                .andReturn();
    }

    @Test
    void givenEmployeeAlreadyExists_whenAddingSameEmployee_throwEmployeeAlreadyExistException() throws Exception {
        final EmployeeDto employeeDto = new EmployeeDto(10L, "mohamed", 23, 2200, "mohamedsasalama@gmailc.om", List.of(), new Department(), 10L, null, null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @AfterEach
    void tearDown() {
        jdbc.execute("DELETE FROM employees");
    }
}