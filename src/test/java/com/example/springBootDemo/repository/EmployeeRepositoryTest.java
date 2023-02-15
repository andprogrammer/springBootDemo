package com.example.springBootDemo.repository;

import com.example.springBootDemo.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

// uses H2

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void employeeExistsWhenAfterSavingToDb() {
        // given
        Employee emp = new Employee();
        employeeRepository.save(emp);

        // when
        boolean employeeExists = employeeRepository.existsById(emp.getId());

        // then
        assertTrue(employeeExists);
    }
}