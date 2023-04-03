package com.example.springBootDemo.repository;

import com.example.springBootDemo.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// uses H2

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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

    @Test
    void employeesExistWhenAfterSavingToDb() {
        // given
        testEntityManager.persist(new Employee());
        testEntityManager.persist(new Employee());

        // when
        List<Employee> employees = employeeRepository.findAll();

        // then
        assertFalse(employees.isEmpty());
        assertEquals(2, employees.size());
    }
}