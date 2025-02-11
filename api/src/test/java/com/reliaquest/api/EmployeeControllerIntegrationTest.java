package com.reliaquest.api;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.reliaquest.api.controller.EmployeeController;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;

@SpringBootTest // Tells Spring Boot to load the application context
public class EmployeeControllerIntegrationTest {

    @Autowired
    private EmployeeController employeeController; // Injecting the actual controller

    @Autowired
    private EmployeeService employeeService; // Injecting the actual service

    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    void setUp() {
        // Set up the test data

        // creating 2 new employees for now
        emp1 = new Employee(UUID.randomUUID().toString(), "Jane Doe", 120000, 29, "Software Engineer", "jane.doe@email.com");
        emp2 = new Employee(UUID.randomUUID().toString(), "John Smith", 100000, 35, "Manager", "john.smith@email.com");
        employeeService.createEmployee(emp1);
        employeeService.createEmployee(emp2);
    }

    @Test
    void testGetAllEmployees() {
        // Get the current list of employees
        List<Employee> initialEmployees = employeeService.getAllEmployees();
        int initialSize = initialEmployees.size();

        // Add two new employees
        employeeService.createEmployee(emp1);
        employeeService.createEmployee(emp2);

        // get the latest list of employees
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        // Check that the size of the list is the initial size plus 2 (since we added 2 employees)
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(initialSize + 2);

        //check that the added employees are included in the list
        assertThat(response.getBody()).contains(emp1, emp2);
    }

    @Test
    void testGetEmployeesByNameSearch() {
        String searchString = "Jane"; // searching for name Jane

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch(searchString);

        // search for employee name and see if it returns the correct body
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(emp1);
    }

    @Test
    void testGetEmployeeById() {
        ResponseEntity<Employee> response = employeeController.getEmployeeById(emp1.getId());
        // get the id and check if it's in the body
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(emp1);
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

        // we made our highest salary 120000, so check if that's correct
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(120000);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();

        // check the top 10, in this case there's only 2
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).contains("Jane Doe", "John Smith");
    }

    @Test
    void testCreateEmployee() {
        // instantiate an employee
        Employee emp3 = new Employee(UUID.randomUUID().toString(), "Alice Williams", 90000, 28, "HR", "alice.williams@email.com");

        // create employee
        ResponseEntity<Employee> response = employeeController.createEmployee(emp3);

        // check that they were created
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(emp3);
    }

    @Test
    void testDeleteEmployeeById() {
        // delete an employee that we made and check if they no longer exist
        ResponseEntity<String> response = employeeController.deleteEmployeeById(emp1.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Jane Doe");
    }
}
