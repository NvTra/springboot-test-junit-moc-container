package com.tranv.springboot.repository;

import com.tranv.springboot.model.Employee;
import com.tranv.springboot.resposiroty.EmployeeRepository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRespositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("John")
                .email("ramesh@tranv.com")
                .build();
    }


    // Junit test for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSaveEmployee() {
        // given - precondition or setup
        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);
        //then - verify the output
//        Assertions.assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    //JUnit test for get all employee operation
    @DisplayName("JUnit test for get all employee operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@tranv.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();
        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployee_whenFindById_thenReturnEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        Employee tempEmployee = employeeRepository.findById(employee.getId()).get();
        //then - verify the output
        assertThat(tempEmployee).isNotNull();

    }

    //JUnit test for get employee by email
    @DisplayName("JUnit test for get employee by email")
    @Test
    public void givenEmployee_whenFindByEmail_thenReturnEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        Employee tempEmployee = employeeRepository.findByEmail("ramesh@tranv.com").get();
        //then - verify the output
        assertThat(tempEmployee).isNotNull();
    }

    //JUnit test for update employee operation
    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdateEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("ram@gmail.com");
        savedEmployee.setFirstName("Ram");
        Employee updateEmployee = employeeRepository.save(savedEmployee);
        //then - verify the output
        assertThat(updateEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updateEmployee.getFirstName()).isEqualTo("Ram");
    }

    //JUnit test for delete employee operation
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        //then - verify the output
        assertThat(employeeOptional).isEmpty();

    }

    //JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        String firstName = "Ramesh";
        String lastName = "John";
        Employee employee1 = employeeRepository.findByJPQL(firstName, lastName);
        //then - verify the output
        assertThat(employee1).isNotNull();
    }

    //JUnit test for custom query using JPQL with named param
    @DisplayName("JUnit test for custom query using JPQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParam_thenReturnEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        String firstName = "Ramesh";
        String lastName = "John";
        Employee employee1 = employeeRepository.findByJPQLNamedParams(firstName, lastName);
        //then - verify the output
        assertThat(employee1).isNotNull();
    }

    //JUnit test for custom query using native SQL with index
    @DisplayName("JUnit test for custom query using SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindBySQL_thenReturnEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        String firstName = "Ramesh";
        String lastName = "John";
        Employee employee1 = employeeRepository.findByNativeSQL(firstName, lastName);
        //then - verify the output
        assertThat(employee1).isNotNull();
    }

    //JUnit test for custom query using native SQL with named params
    @DisplayName("JUnit test for custom query using SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindBySQLNamedParams_thenReturnEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behaviour that we are going test
        String firstName = "Ramesh";
        String lastName = "John";
        Employee employee1 = employeeRepository.findByNativeSQLNamedParams(firstName, lastName);
        //then - verify the output
        assertThat(employee1).isNotNull();
    }
}
