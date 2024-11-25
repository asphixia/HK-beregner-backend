package com.example.hksalarycalculatorbackend;

import com.example.hksalarycalculatorbackend.model.Employee;
import com.example.hksalarycalculatorbackend.model.HKMemberRole;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DroolsRuleTest {

    @Autowired
    private KieSession kieSession;

    @Test
    public void testFullTimeEmploymentRule() {
        Employee employee = new Employee();
        employee.setHkMemberRole(HKMemberRole.STOREWORKER);
        employee.setHoursWorked(160); // Example hours
        kieSession.insert(employee);
        int firedRules = kieSession.fireAllRules();
        System.out.println("Number of rules fired: " + firedRules);
        System.out.println("Overtime eligible: " + employee.isOvertimeEligible());
    }
}