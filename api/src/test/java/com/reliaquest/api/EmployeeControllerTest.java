package com.reliaquest.api;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.reliaquest.api.controller.EmployeeController;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;

@ExtendWith(MockitoExtension.class)  // Tells JUnit to use Mockito
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService; // Mocked service

    @InjectMocks
    private EmployeeController employeeController;

    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    void setUp() {
        emp1 = new Employee(UUID.randomUUID().toString(), "Jane Doe", 120000, 29, "Software Engineer", "jane.doe@email.com");
        emp2 = new Employee(UUID.randomUUID().toString(), "John Smith", 100000, 35, "Manager", "john.smith@email.com");
    }

    @Test
    void testGetAllEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp1, emp2));

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).contains(emp1, emp2);
    }

    @Test
    void testGetEmployeesByNameSearch() {
        String searchString = "Jane";

        when(employeeService.getEmployeesByNameSearch(searchString)).thenReturn(Arrays.asList(emp1));

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch(searchString);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).contains(emp1);
    }

    @Test
    void testGetEmployeeById() {
        when(employeeService.getEmployeeById(emp1.getId())).thenReturn(emp1);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(emp1.getId());

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(emp1);
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(120000);

        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(120000);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        when(employeeService.getTop10HighestEarningEmployeeNames()).thenReturn(Arrays.asList("Jane Doe", "John Smith"));

        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).contains("Jane Doe", "John Smith");
    }

    @Test
    void testCreateEmployee() {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(emp1);

        ResponseEntity<Employee> response = employeeController.createEmployee(emp1);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(emp1);
    }

    @Test
    void testDeleteEmployeeById() {
        when(employeeService.deleteEmployeeById(emp1.getId())).thenReturn("Employee deleted successfully");

        ResponseEntity<String> response = employeeController.deleteEmployeeById(emp1.getId());

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Employee deleted successfully");
    }
}
