package com.skypro.employeebookspring.service;


import com.skypro.employeebookspring.exeption.EmployeeNotFoundException;
import com.skypro.employeebookspring.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmenService {
    private final EmployeeService employeeService;

    public DepartmenService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Collection<Employee> getDepartmentEmployees(int department){
        return getEmployeeByDepartmentStream(department).collect(Collectors.toList());
    }

    public int getSumOfSalaryByDepartment(int department){
        return getEmployeeByDepartmentStream(department)
                .mapToInt(Employee::getSalary).sum();
    }

    public int getMaxOfSalaryByDepartment(int department){
        return getEmployeeByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .max()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public int getMinOfSalaryByDepartment(int department){
        return getEmployeeByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .min()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartaments(){
        return employeeService.getAllEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    private Stream<Employee> getEmployeeByDepartmentStream(int department){
        return employeeService.getAllEmployees()
                .stream()
                .filter(e -> e.getDepartment() == department);
    }
}
