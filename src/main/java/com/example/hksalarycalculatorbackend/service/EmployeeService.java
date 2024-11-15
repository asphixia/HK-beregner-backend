package com.example.hksalarycalculatorbackend.service;

import com.example.hksalarycalculatorbackend.model.Employee;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private KieContainer kieContainer;

    public void processEmployee(Employee employee) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(employee);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
