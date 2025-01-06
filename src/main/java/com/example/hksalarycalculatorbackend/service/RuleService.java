package com.example.hksalarycalculatorbackend.service;

import com.example.hksalarycalculatorbackend.config.DroolsConfig;
import com.example.hksalarycalculatorbackend.dto.RuleDTO;
import com.example.hksalarycalculatorbackend.model.AdminAction;
import com.example.hksalarycalculatorbackend.model.AdminActionType;
import com.example.hksalarycalculatorbackend.model.Rule;
import com.example.hksalarycalculatorbackend.model.Status;
import com.example.hksalarycalculatorbackend.repositories.RuleRepository;
import com.example.hksalarycalculatorbackend.utils.RuleValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private DroolsConfig droolsConfig;

    @Autowired
    private AdminActionService adminActionService;


    public List<Rule> getAllRules() {
        List<Rule> rules = new ArrayList<>();
        Iterable<Rule> getAllRulesIterable = ruleRepository.findAll();
        for (Rule rule : getAllRulesIterable) {
            rules.add(rule);
        }
        return rules;
    }


    public Rule updateRule(Long id, RuleDTO ruleDTO, String username) {
        Rule existingRule = ruleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found with ID: " + id));
        existingRule.setRuleName(ruleDTO.getRuleName());
        existingRule.setConditions(ruleDTO.getConditions());
        existingRule.setActions(ruleDTO.getActions());
        ruleRepository.save(existingRule);
        adminActionService.createAdminAction(
                username,
                AdminActionType.UPDATED,
                ruleDTO.getRuleName()
        );
        Iterable<Rule> rules = ruleRepository.findAll();
        droolsConfig.createOrUpdateKieBase(rules);
        return existingRule;
    }

    public Rule createRule(RuleDTO ruleDTO) {

        Rule rule = new Rule();
        rule.setRuleName(ruleDTO.getRuleName());
        rule.setConditions(ruleDTO.getConditions());
        rule.setActions(ruleDTO.getActions());
        rule.setStatus(ruleDTO.isStatus());
        Rule savedRule = ruleRepository.save(rule);

        Iterable<Rule> rules = ruleRepository.findAll();

        rules.forEach(r -> {
            System.out.println("Rule: " + r.getRuleName());
            System.out.println("Conditions: " + r.getConditions());
            System.out.println("Actions: " + r.getActions());
        });

        droolsConfig.createOrUpdateKieBase(rules);

        return savedRule;
    }

    public Rule getRuleById(Long id) {
        return ruleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rule not found with id: " + id));
    }

    public void deleteRule(Long id) {
        Rule rule = ruleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rule not found with id: " + id));
        rule.setStatus(false);
        ruleRepository.save(rule);

        Iterable<Rule> rules = ruleRepository.findAll();
        droolsConfig.createOrUpdateKieBase(rules);
    }

    public void validateJson(String json) {
        try {
            new ObjectMapper().readTree(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON format: " + json, e);
        }
    }
}
