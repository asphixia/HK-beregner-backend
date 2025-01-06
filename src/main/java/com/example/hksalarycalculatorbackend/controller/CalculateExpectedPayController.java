package com.example.hksalarycalculatorbackend.controller;

import com.example.hksalarycalculatorbackend.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.hksalarycalculatorbackend.service.CalculateExpectedPayService;

@RestController
@RequestMapping("/api")
public class CalculateExpectedPayController {

    @Autowired
    private CalculateExpectedPayService calculateExpectedPayService;

    @PostMapping("/calculatexsalary")
    public ResponseEntity<Employee> calculateExpectedSalary(@RequestBody Employee employee) {
        Employee updatedEmployee = calculateExpectedPayService.calculateExpectedPay(employee);
        return ResponseEntity.ok(updatedEmployee);
    }
}
