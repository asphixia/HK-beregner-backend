package com.example.hksalarycalculatorbackend.service;


import com.example.hksalarycalculatorbackend.model.Employee;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateExpectedPayService {

    @Autowired
    private KieBase kieBase;

    public Employee calculateExpectedPay(Employee employee) {
        // Create a new KieSession from the KieBase
        KieSession kieSession = kieBase.newKieSession();

        // Insert the employee into the session
        kieSession.insert(employee);

        // Add a listener for debugging rule execution
        kieSession.addEventListener(new org.kie.api.event.rule.DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(org.kie.api.event.rule.AfterMatchFiredEvent event) {
                System.out.println("Rule fired: " + event.getMatch().getRule().getName());
            }
        });

        // Fire all rules and dispose of the session
        kieSession.fireAllRules();
        kieSession.dispose();

        return employee;
    }
}