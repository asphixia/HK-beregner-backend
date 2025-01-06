package com.example.hksalarycalculatorbackend.config;

import com.example.hksalarycalculatorbackend.model.Rule;
import com.example.hksalarycalculatorbackend.repositories.RuleRepository;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {

    @Autowired
    private RuleRepository ruleRepository;

    private KieContainer kieContainer;

    @Bean
    public KieBase kieBase() {
        if (kieContainer == null) {
            createOrUpdateKieBase(ruleRepository.findAll());
        }
        return kieContainer.getKieBase();
    }
    public void createOrUpdateKieBase(Iterable<Rule> rules) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        for (Rule rule : rules) {
            String drl = generateDrl(rule);
            kieFileSystem.write("src/main/resources/rules/" + rule.getRuleName() + ".drl", drl);
        }

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("Error compiling rules: " + results.getMessages());
        }
        this.kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
    }
        private String generateDrl(Rule rule) {
            String drl = "package com.example.hksalarycalculatorbackend.rules;\n" +
                    "import com.example.hksalarycalculatorbackend.model.Employee;\n" +
                    "import com.example.hksalarycalculatorbackend.model.HKMemberRole;\n" +
                    "rule \"" + rule.getRuleName() + "\"\n" +
                    "when\n" + rule.getConditions() + "\n" +
                    "then\n" + rule.getActions() + ";\n" +
                    "end";
            System.out.println("Generated DRL:\n" + drl);
            return drl;
        }
}
