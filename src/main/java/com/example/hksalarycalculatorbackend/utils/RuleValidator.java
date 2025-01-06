package com.example.hksalarycalculatorbackend.utils;

import com.example.hksalarycalculatorbackend.model.Rule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.springframework.stereotype.Component;

@Component
public class RuleValidator {

    public void validate(Rule rule){
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        String drl = generateDrl(rule);
        kieFileSystem.write("src/main/resources/temp.drl", drl);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalArgumentException("Invalid rule: " + results.getMessages());
        }
    }

    private String generateDrl(Rule rule) {
        return "rule \"" + rule.getRuleName() + "\"\n" +
                "when\n" +
                rule.getConditions() + "\n" +
                "then\n" +
                rule.getActions() + "\n" +
                "end";
    }
}
