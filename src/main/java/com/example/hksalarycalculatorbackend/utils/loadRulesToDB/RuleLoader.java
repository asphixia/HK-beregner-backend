package com.example.hksalarycalculatorbackend.utils.loadRulesToDB;

import com.example.hksalarycalculatorbackend.config.DroolsConfig;
import com.example.hksalarycalculatorbackend.model.Rule;
import com.example.hksalarycalculatorbackend.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RuleLoader extends DroolsConfig {

    @Autowired
    private RuleRepository ruleRepository;

    public void loadSampleRule() {
        Rule rule = new Rule();
        rule.setRuleName("Calculate Base Salary - Full-time");
        rule.setConditions("$employee: Employee(hkMemberRole == HKMemberRole.MIDDLEMANAGER || hkMemberRole == HKMemberRole.STOREWORKER)");
        rule.setActions("double monthlySalary = $employee.getAverageWeeklyHours() >= 37 ? 23132.00 : 0.0;\n"
                + "$employee.setCalculatedSalary(monthlySalary);\n"
                + "update($employee);");

        ruleRepository.save(rule);
        System.out.println("Sample rule saved successfully.");
    }   

}