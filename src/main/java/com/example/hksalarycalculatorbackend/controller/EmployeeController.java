package com.example.hksalarycalculatorbackend.controller;

import com.example.hksalarycalculatorbackend.model.Employee;
import com.example.hksalarycalculatorbackend.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processEmployee(@RequestBody Employee employee) {
        try {
            employeeService.processEmployee(employee);
            String responseMessage = "Employee processed successfully. Overtime Eligible: " + employee.isOvertimeEligible();
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process employee: " + e.getMessage());
        }
    }
}
