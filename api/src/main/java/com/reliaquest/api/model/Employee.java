package com.reliaquest.api.model;

public class Employee {

    private String id;
    private String employeeName;
    private int employeeSalary;
    private int employeeAge;
    private String employeeTitle;
    private String employeeEmail;

    // Constructor for creating an Employee object class.
    public Employee(String id, String employeeName, int employeeSalary, int employeeAge, String employeeTitle, String employeeEmail) {
        this.id = id;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeeAge = employeeAge;
        this.employeeTitle = employeeTitle;
        this.employeeEmail = employeeEmail;
    }

    // Getter and setter for Employee id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and setter for Employee name
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


    // Getter and setter for Employee Salary
    public int getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(int employeeSalary) {
        this.employeeSalary = employeeSalary;
    }


    // Getter and setter for Employee Age
    public int getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }


    // Getter and setter for Employee title
    public String getEmployeeTitle() {
        return employeeTitle;
    }

    public void setEmployeeTitle(String employeeTitle) {
        this.employeeTitle = employeeTitle;
    }


    // Getter and setter for Employee Email
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
