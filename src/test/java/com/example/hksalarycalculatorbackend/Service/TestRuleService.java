package com.example.hksalarycalculatorbackend.Service;

import com.example.hksalarycalculatorbackend.config.DroolsConfig;
import com.example.hksalarycalculatorbackend.dto.RuleDTO;
import com.example.hksalarycalculatorbackend.model.Rule;
import com.example.hksalarycalculatorbackend.model.AdminActionType;
import com.example.hksalarycalculatorbackend.repositories.RuleRepository;
import com.example.hksalarycalculatorbackend.service.AdminActionService;
import com.example.hksalarycalculatorbackend.service.RuleService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestRuleService {

    @InjectMocks
    private RuleService ruleService;

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private DroolsConfig droolsConfig;

    @Mock
    private AdminActionService adminActionService;

    private Rule rule;
    private RuleDTO ruleDTO;

    @BeforeEach
    void setUp() {
        rule = new Rule();
        rule.setId(1L);
        rule.setRuleName("Test Rule");
        rule.setConditions("Conditions");
        rule.setActions("Actions");
        rule.setStatus(true);

        ruleDTO = new RuleDTO();
        ruleDTO.setRuleName("Updated Rule");
        ruleDTO.setConditions("Updated Conditions");
        ruleDTO.setActions("Updated Actions");
        ruleDTO.setStatus(true);
    }

    @Test
    void getAllRules_ShouldReturnListOfRules() {
        when(ruleRepository.findAll()).thenReturn(Arrays.asList(rule));

        List<Rule> rules = ruleService.getAllRules();

        assertNotNull(rules);
        assertEquals(1, rules.size());
        assertEquals("Test Rule", rules.get(0).getRuleName());
        verify(ruleRepository, times(1)).findAll();
    }

    @Test
    void updateRule_ShouldUpdateAndReturnUpdatedRule() {
        when(ruleRepository.findById(1L)).thenReturn(Optional.of(rule));
        when(ruleRepository.save(any(Rule.class))).thenReturn(rule);

        Rule updatedRule = ruleService.updateRule(1L, ruleDTO, "admin");

        assertNotNull(updatedRule);
        assertEquals("Updated Rule", updatedRule.getRuleName());
        assertEquals("Updated Conditions", updatedRule.getConditions());
        assertEquals("Updated Actions", updatedRule.getActions());

        verify(ruleRepository, times(1)).findById(1L);
        verify(ruleRepository, times(1)).save(any(Rule.class));
        verify(adminActionService, times(1)).createAdminAction("admin", AdminActionType.UPDATED, "Updated Rule");
        verify(droolsConfig, times(1)).createOrUpdateKieBase(any());
    }

    @Test
    void createRule_ShouldSaveAndReturnNewRule() {
        when(ruleRepository.save(any(Rule.class))).thenReturn(rule);
        when(ruleRepository.findAll()).thenReturn(Arrays.asList(rule));

        Rule createdRule = ruleService.createRule(ruleDTO);

        assertNotNull(createdRule);
        assertEquals("Test Rule", createdRule.getRuleName());

        verify(ruleRepository, times(1)).save(any(Rule.class));
        verify(droolsConfig, times(1)).createOrUpdateKieBase(any());
    }

    @Test
    void getRuleById_ShouldReturnRule_WhenRuleExists() {
        when(ruleRepository.findById(1L)).thenReturn(Optional.of(rule));

        Rule foundRule = ruleService.getRuleById(1L);

        assertNotNull(foundRule);
        assertEquals("Test Rule", foundRule.getRuleName());
        verify(ruleRepository, times(1)).findById(1L);
    }

    @Test
    void getRuleById_ShouldThrowException_WhenRuleDoesNotExist() {
        when(ruleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ruleService.getRuleById(1L));
        verify(ruleRepository, times(1)).findById(1L);
    }

    @Test
    void deleteRule_ShouldMarkRuleAsInactive() {
        when(ruleRepository.findById(1L)).thenReturn(Optional.of(rule));

        ruleService.deleteRule(1L);

        assertFalse(rule.isStatus());
        verify(ruleRepository, times(1)).findById(1L);
        verify(ruleRepository, times(1)).save(rule);
        verify(droolsConfig, times(1)).createOrUpdateKieBase(any());
    }

    @Test
    void validateJson_ShouldNotThrowException_WhenJsonIsValid() {
        String validJson = "{\"key\": \"value\"}";

        assertDoesNotThrow(() -> ruleService.validateJson(validJson));
    }

    @Test
    void validateJson_ShouldThrowException_WhenJsonIsInvalid() {
        String invalidJson = "{key: value}";

        assertThrows(IllegalArgumentException.class, () -> ruleService.validateJson(invalidJson));
    }
}