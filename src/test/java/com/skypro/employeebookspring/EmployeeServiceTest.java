package com.skypro.employeebookspring;

import com.skypro.employeebookspring.model.Employee;
import com.skypro.employeebookspring.record.EmployeeRequest;
import com.skypro.employeebookspring.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {
    private EmployeeService employeeService;


    @BeforeEach
    public void setUp(){
        this.employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("TestOne","TestSix",1,5000),
                new EmployeeRequest("TestTwo","TestSeven",1,6000),
                new EmployeeRequest("TestThree","TestEight",2,7000),
                new EmployeeRequest("TestFour","TestNine",2,8000),
                new EmployeeRequest("TestFive","TestNone",2,9000)
        ).forEach(employeeService::addEmployee);
    }

    @Test
    public void addEmployee(){
        EmployeeRequest request = new EmployeeRequest("Valid","Valid",3,5000);
        Employee result = employeeService.addEmployee(request);
        assertEquals(request.getFirstName(),result.getFirstName());
        assertEquals(request.getLastName(),result.getLastName());
        assertEquals(request.getDepartment(),result.getDepartment());
        assertEquals(request.getSalary(),result.getSalary());


        assertThat(employeeService.getAllEmployees())
                .contains(result);
    }
    @Test
    public void listEmployee(){
        Collection<Employee> employees = employeeService.getAllEmployees();
        assertThat(employees).hasSize(5);
        assertThat(employees)
                .first()
                .extracting(Employee::getFirstName)
                .isEqualTo("TestOne");
    }
    @Test
    public void sumOfSalary() {
        int sum = employeeService.getSalarySum();
        assertThat(sum).isEqualTo(35_000);
    }

    @Test
    public void employeeWithMaxSalary() {
        Employee employee = employeeService.getEmployeeWithMaxSalary();
        assertThat(employee).
                isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("TestFive");
    }

    @Test
    public void employeeWithMinSalary() {
        Employee employee = employeeService.getEmployeeWithMinSalary();
        assertThat(employee).
                isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("TestOne");
    }

    @Test
    public void AverageSalary(){
        Double sum = employeeService.getAverageSalary();
        assertThat(sum).isEqualTo(7000);
    }

    @Test
    public void EmployeeWithSalaryMoreThatAverage(){
        Collection<Employee> employees = employeeService.getEmployeeWithSalaryMoreThatAverage();
        assertThat(employees)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .toString();
    }

    @Test
     void removeEmployee(){
        employeeService.removeEmployee(0);
        Collection<Employee> employees = employeeService.getAllEmployees();
        assertThat(employees).hasSize(5);
    }
}
