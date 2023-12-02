package com.tranv.springboot.service;

import com.tranv.springboot.model.Employee;
import com.tranv.springboot.resposiroty.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

public class EmployeeServiceTests {
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    //JUnit test for save Employee method
    @DisplayName("JUnit test for save Employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObj() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Ramesh")
                .lastName("John")
                .email("ramesh@tranv.com")
                .build();
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        // gọi phương thức findByEmail() trên đối tượng employeeRepository với giá trị của employee.getEmail(), kết quả trả về sẽ là Optional.empty().

        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);
        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);
        //then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
    }
}
