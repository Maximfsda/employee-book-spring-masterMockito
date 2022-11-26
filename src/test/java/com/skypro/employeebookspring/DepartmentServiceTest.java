package com.skypro.employeebookspring;


import com.skypro.employeebookspring.exeption.EmployeeNotFoundException;
import com.skypro.employeebookspring.model.Employee;
import com.skypro.employeebookspring.service.DepartmenService;
import com.skypro.employeebookspring.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees =List.of(
            new Employee("TestOne","TestSix",1,5000),
            new Employee("TestTwo","TestSeven",1,6000),
            new Employee("TestThree","TestEight",1,7000),
            new Employee("TestFour","TestNine",2,8000),
            new Employee("TestFive","TestNone",2,9000)
    );
    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmenService departmentService;

    @BeforeEach
    void setUp(){
        when(employeeService.getAllEmployees())
                .thenReturn(employees);

    }
    @Test
    void getEmployeesByDepartment(){
        Collection<Employee> employeeList = this.departmentService.getDepartmentEmployees(1);
        assertThat(employeeList)
                .hasSize(3)
                .contains(employees.get(0),
                        employees.get(1),
                        employees.get(2));
    }

    @Test
    void sumOfSalaryByDepartment(){
        int sum = this.departmentService.getSumOfSalaryByDepartment(1);
        assertThat(sum).isEqualTo(18_000);
    }

    @Test
    void employeeWithMaxSalaryInDepartment(){
        int max = this.departmentService.getMaxOfSalaryByDepartment(2);
        assertThat(max).isEqualTo(9000);
    }

    @Test
    void employeeWithMinSalaryInDepartment(){
        int min = this.departmentService.getMinOfSalaryByDepartment(2);
        assertThat(min).isEqualTo(8000);
    }

    @Test
    void groupedEmployees(){
        Map<Integer,List<Employee>> groupedEmployees = this.departmentService.getEmployeesGroupedByDepartaments();

        assertThat(groupedEmployees)
                .hasSize(2)
                .containsEntry(1,List.of(employees.get(0),employees.get(1),employees.get(2)))
                .containsEntry(2,List.of(employees.get(3),employees.get(4)));
    }

    @Test
    void WhenNoEmployeesThenGroupByReturnEmptyMap(){
        when(employeeService.getAllEmployees()).thenReturn(List.of());

        Map<Integer, List<Employee>> groupedEmployees = this.departmentService
                .getEmployeesGroupedByDepartaments();

        assertThat(groupedEmployees).isEmpty();
    }

    @Test
    void WhenNoEmployeesThenMaxSalaryInDepartmentThrowsException(){
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        Assertions.assertThatThrownBy(()-> departmentService.getMaxOfSalaryByDepartment(0))
                .isInstanceOf(EmployeeNotFoundException.class);

    }
}
