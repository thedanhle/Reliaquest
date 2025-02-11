package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    // In-memory storage for employees (you could replace this with a database)
    private final List<Employee> employees = new ArrayList<>();

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return employees;
    }

    // Search employees by name
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Get employee by ID
    public Employee getEmployeeById(String id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null); // Return null if not found
    }

    // Get the highest salary among all employees
    public Integer getHighestSalaryOfEmployees() {
        return employees.stream().mapToInt(Employee::getEmployeeSalary).max().orElse(0); // Return 0 if no employees
    }

    // Get top 10 highest earning employee names
    public List<String> getTop10HighestEarningEmployeeNames() {
        return employees.stream()
                .sorted((e1, e2) -> Integer.compare(e2.getEmployeeSalary(), e1.getEmployeeSalary())) // Sort descending by salary
                .limit(10)
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());
    }

    // Create a new employee. User passes in Employee object and we'll generate a new id for them
    public Employee createEmployee(Employee employeeInput) {
        String id = UUID.randomUUID().toString();
        employeeInput.setId(id);
        employees.add(employeeInput);
        return employeeInput;
    }

    // Delete an employee by ID
    public String deleteEmployeeById(String id) {
        Employee employee = getEmployeeById(id);
        if (employee != null) {
            employees.remove(employee);
            return employee.getEmployeeName();
        } else {
            return "Employee not found";
        }
    }
}
