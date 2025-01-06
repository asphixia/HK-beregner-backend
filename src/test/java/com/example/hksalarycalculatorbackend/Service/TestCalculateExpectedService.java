package com.example.hksalarycalculatorbackend.Service;

import com.example.hksalarycalculatorbackend.model.Employee;
import com.example.hksalarycalculatorbackend.service.CalculateExpectedPayService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Match;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestCalculateExpectedService {

    @InjectMocks
    private CalculateExpectedPayService calculateExpectedPayService;

    @Mock
    private KieBase kieBase;

    @Mock
    private KieSession kieSession;

    @Test
    void calculateExpectedPay_ShouldReturnModifiedEmployee() {
        Employee employee = new Employee();
        employee.setCalculatedSalary(50000);

        when(kieBase.newKieSession()).thenReturn(kieSession);

        doAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            emp.setCalculatedSalary(55000);
            return null;
        }).when(kieSession).insert(any(Employee.class));

        doNothing().when(kieSession).fireAllRules();
        doNothing().when(kieSession).dispose();

        Employee result = calculateExpectedPayService.calculateExpectedPay(employee);

        assertNotNull(result);
        assertEquals(55000, result.getCalculatedSalary());

        verify(kieBase, times(1)).newKieSession();
        verify(kieSession, times(1)).insert(employee);
        verify(kieSession, times(1)).fireAllRules();
        verify(kieSession, times(1)).dispose();
    }

    @Test
    void calculateExpectedPay_ShouldHandleNoRulesFired() {
        Employee employee = new Employee();
        employee.setCalculatedSalary(60000);

        when(kieBase.newKieSession()).thenReturn(kieSession);
        doNothing().when(kieSession).fireAllRules();
        doNothing().when(kieSession).dispose();

        Employee result = calculateExpectedPayService.calculateExpectedPay(employee);

        assertNotNull(result);
        assertEquals(60000, result.getCalculatedSalary());

        verify(kieBase, times(1)).newKieSession();
        verify(kieSession, times(1)).insert(employee);
        verify(kieSession, times(1)).fireAllRules();
        verify(kieSession, times(1)).dispose();
    }
}
