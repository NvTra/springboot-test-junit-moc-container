package com.tranv.springboot.service;

import com.tranv.springboot.exception.ResourceNotFoundException;
import com.tranv.springboot.model.Employee;
import com.tranv.springboot.resposiroty.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.module.ResolutionException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceAnnotationTests {
    @Mock //tạo ra đối tượng ảo
    private EmployeeRepository employeeRepository;

    @InjectMocks // truyển phụ thuộc vào trong đôi tượng ảo
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Ramesh")
                .lastName("John")
                .email("ramesh@tranv.com")
                .build();
    }

    //JUnit test for save Employee method
    @DisplayName("JUnit test for save Employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObj() {
        // given - precondition or setup

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

    //JUnit test for save Employee method
    @DisplayName("JUnit test for save Employee method with throw exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenReturnThrowNewExc() {
        // given - precondition or setup

        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        // gọi phương thức findByEmail() trên đối tượng employeeRepository với giá trị của employee.getEmail(), kết quả trả về sẽ là 1 đối tượng employee

        System.out.println(employeeRepository);
        System.out.println(employeeService);
        // when - action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.saveEmployee(employee));
        //then
        BDDMockito.verify(employeeRepository, Mockito.never()).save(ArgumentMatchers.any(Employee.class));
    }

    //JUnit test for getAllEmployee method
    @DisplayName("JUnit test for getAllEmployee method")
    @Test
    public void givenEmployeeList_whenAllEmployee_thenGetAllEmployee() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tranv")
                .lastName("John")
                .email("tranv@tranv.com")
                .build();
        BDDMockito.given(employeeService.getAllEmployee()).willReturn(List.of(employee, employee1));
        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployee();
        //then - verify the output
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    //JUnit test for getAllEmployee method
    @DisplayName("JUnit test for getAllEmployee method (negative scenario)")
    @Test
    public void givenEmptyEmployeeList_whenAllEmployee_thenReturnEmptyAllEmployee() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tranv")
                .lastName("John")
                .email("tranv@tranv.com")
                .build();
        BDDMockito.given(employeeService.getAllEmployee()).willReturn(Collections.emptyList());
        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployee();
        //then - verify the output
        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);
    }

    //JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
        // given - precondition or setup
        BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        // when - action or the behaviour that we are going test
        Employee employee1 = employeeService.getEmployeeById(employee.getId()).get();
        //then - verify the output
        Assertions.assertThat(employee1).isNotNull();
    }

    //JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployee() {
        // given - precondition or setup
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        employee.setFirstName("Ram");
        employee.setEmail("ram@gmail.com");
        // when - action or the behaviour that we are going test

        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output

        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }

    //JUnit test for delete Employee method
    @Test
    public void givenEmployeeObj_whenDeleteEmployee_thenNothing() {
        // given - precondition or setup
        long employeeId = 1L;
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(employeeId);
        // when - action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);
        //then - verify the output
        BDDMockito.verify(employeeRepository, Mockito.times(1)).deleteById(employeeId);
    }
}
