package com.example.springBootDemo.controller;

import com.example.springBootDemo.exception.ResouceNotFoundException;
import com.example.springBootDemo.model.Employee;
import com.example.springBootDemo.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResouceNotFoundException("Employee not found" + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResouceNotFoundException("Employee not found for this id :: " + employeeId));
        employee.setName(employeeDetails.getName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);

    }

    @DeleteMapping("employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResouceNotFoundException("Employee not found" + employeeId));
        employeeRepository.delete(employee);
        return ResponseEntity.ok().build();
    }
}
