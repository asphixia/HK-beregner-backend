package com.example.hksalarycalculatorbackend;

import com.example.hksalarycalculatorbackend.model.Employee;
import com.example.hksalarycalculatorbackend.model.HKMemberRole;
import com.example.hksalarycalculatorbackend.model.Rule;
import com.example.hksalarycalculatorbackend.repositories.RuleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RuleIntegrationTest {
    @Autowired
    private RuleRepository ruleRepository;

    @Test
    public void testLoadAndApplyRule(){
        Rule rule = ruleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Rule not found"));

        String drl = "package com.example.hksalarycalculatorbackend.rules;\n" +
                "import com.example.hksalarycalculatorbackend.model.Employee;\n" +
                "import com.example.hksalarycalculatorbackend.model.HKMemberRole;\n" +
                "rule \"" + rule.getRuleName() + "\"\n" +
                "when\n" +
                rule.getConditions() + "\n" +
                "then\n" +
                rule.getActions() + "\n" +
                "end";
        System.out.println("Generated DRL:\n" + drl);

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write("src/main/resources/test_rule.drl", drl);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("Errors in rule compilation: " + results.getMessages());
        }

        KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
        KieSession kieSession = kieContainer.newKieSession();

        Employee employee = new Employee();
        employee.setHkMemberRole(HKMemberRole.MIDDLEMANAGER);
        employee.setAverageWeeklyHours(38);

        System.out.println(employee);
        kieSession.insert(employee);
        kieSession.fireAllRules();
        kieSession.dispose();

        System.out.println(employee.getCalculatedSalary());
        Assertions.assertEquals(23132.00, employee.getCalculatedSalary(), "The calculated salary does not match the expected value!");

    }

}
