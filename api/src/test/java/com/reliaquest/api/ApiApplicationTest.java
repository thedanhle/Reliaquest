package com.reliaquest.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;

@SpringBootTest
class ApiApplicationTest {

    @Autowired
    private EmployeeService employeeService;
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        // Prepare the test employee
        testEmployee = new Employee("12345","Danh Le", 100000, 100, "Software Engineer", "danh.le.engineer@gmail.com");

        // Clear the employees list to avoid interference from previous tests
        employeeService.getAllEmployees().clear();
    }

    @Test
    void testCreateEmployee() {
        // Call the service to create an employee
        Employee createdEmployee = employeeService.createEmployee(testEmployee);

        // Check if the employee is created correctly. Just checking these 3 for now
        assertNotNull(createdEmployee.getId()); // Ensure the employee has an ID
        assertEquals("Danh Le", createdEmployee.getEmployeeName()); // Check the employee name
        assertEquals(100000, createdEmployee.getEmployeeSalary()); // Check the employee salary
    }

    @Test
    void testGetHighestSalary() {
        // Add employee to service
        employeeService.createEmployee(testEmployee);

        // Get the highest salary from the service
        int highestSalary = employeeService.getHighestSalaryOfEmployees();

        // Assert that the highest salary is as expected
        assertEquals(100000, highestSalary);
    }

    @Test
    void testGetAllEmployees() {
        // Verify the list is empty before adding
        assertEquals(0, employeeService.getAllEmployees().size());

        // Add the employee to the service
        employeeService.createEmployee(testEmployee);

        // Verify the size of the employee list
        assertEquals(1, employeeService.getAllEmployees().size());

        // Verify the name of the first employee in the list
        assertEquals("Danh Le", employeeService.getAllEmployees().get(0).getEmployeeName());
    }

    @Test
    void testGetEmployeeById() {
        // Add employee to service
        Employee createdEmployee = employeeService.createEmployee(testEmployee);

        // Retrieve the employee by ID and verify the result
        Employee retrievedEmployee = employeeService.getEmployeeById(createdEmployee.getId());
        assertNotNull(retrievedEmployee);
        assertEquals("Danh Le", retrievedEmployee.getEmployeeName());
    }

    @Test
    void testGetEmployeesByNameSearch() {
        // Add employee to service
        employeeService.createEmployee(testEmployee);

        // Search by employee name and verify the result
        assertEquals(1, employeeService.getEmployeesByNameSearch("Danh").size());
        assertEquals("Danh Le", employeeService.getEmployeesByNameSearch("Danh").get(0).getEmployeeName());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        // Add employee to service
        employeeService.createEmployee(testEmployee);

        // Retrieve the top 10 highest earning employee names
        assertEquals(1, employeeService.getTop10HighestEarningEmployeeNames().size());
        assertEquals("Danh Le", employeeService.getTop10HighestEarningEmployeeNames().get(0));
    }

    @Test
    void testDeleteEmployeeById() {
        // Add employee to service
        Employee createdEmployee = employeeService.createEmployee(testEmployee);

        // Delete employee by ID
        String deletedEmployeeName = employeeService.deleteEmployeeById(createdEmployee.getId());

        // Assert the deletion result
        assertEquals("Danh Le", deletedEmployeeName);
        assertEquals(0, employeeService.getAllEmployees().size()); // List should be empty
    }
}
