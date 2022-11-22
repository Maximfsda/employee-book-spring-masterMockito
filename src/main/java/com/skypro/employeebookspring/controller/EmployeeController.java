package com.skypro.employeebookspring.controller;


import com.skypro.employeebookspring.exeption.EmployeeNotFoundException;
import com.skypro.employeebookspring.model.Employee;
import com.skypro.employeebookspring.record.EmployeeRequest;
import com.skypro.employeebookspring.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;

/**
 * HTTP методы
 * GET - получение ресурса или создание ресурса
 * POST - создание русурсов
 * PUT - модификация  русурсов
 * PATCH - Частичная модификация  русурсов
 * DELETE - удаление  русурсов
 */

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Collection<Employee> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return this.employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("/employees/salary/sum")
    public int getSalarySum() {
        return this.employeeService.getSalarySum();
    }
    @GetMapping("/employees/salary/min")
    public Employee getSalaryMin(){
        return this.employeeService.getEmployeeWithMinSalary();
    }
    @GetMapping("/employees/salary/max")
    public Employee getSalaryMax(){
        return this.employeeService.getEmployeeWithMaxSalary();
    }
    @GetMapping("/employees/highSalary")
    public List<Employee> getEmployeeWithSalaryMoreThatAverage() {
        return this.employeeService.getEmployeeWithSalaryMoreThatAverage();
    }

}
