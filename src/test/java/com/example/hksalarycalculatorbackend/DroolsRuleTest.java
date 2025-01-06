package com.example.hksalarycalculatorbackend;

import com.example.hksalarycalculatorbackend.model.Employee;
import com.example.hksalarycalculatorbackend.model.HKMemberRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class DroolsRuleTest {

    @Test
    public void testRule(){
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        String drl = "package com.example.hksalarycalculatorbackend.rules;\n" +
                "import com.example.hksalarycalculatorbackend.model.Employee;\n" +
                "import com.example.hksalarycalculatorbackend.model.HKMemberRole;\n" +
                "rule \"Test Rule\"\n" +
                "when\n" +
                "$employee: Employee(hkMemberRole == HKMemberRole.MIDDLEMANAGER || hkMemberRole == HKMemberRole.STOREWORKER)\n" +
                "then\n" +
                "double monthlySalary = $employee.getAverageWeeklyHours() >= 37 ? 23132.00 : 0.0;\n" +
                "$employee.setCalculatedSalary(monthlySalary);\n" +
                "update($employee);\n" +
                "end";

        kieFileSystem.write("src/main/resources/test.drl", drl);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        if (kieBuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            throw new IllegalStateException("Errors building KieBase: " + kieBuilder.getResults().toString());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();
        Employee employee = new Employee();
        employee.setHkMemberRole(HKMemberRole.MIDDLEMANAGER);
        employee.setAverageWeeklyHours(38);
        kieSession.insert(employee);
        kieSession.fireAllRules();
        kieSession.dispose();
        Assertions.assertEquals(23132.00, employee.getCalculatedSalary());







    }



}