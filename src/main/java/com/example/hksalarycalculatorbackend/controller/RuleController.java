package com.example.hksalarycalculatorbackend.controller;

import com.example.hksalarycalculatorbackend.dto.RuleDTO;
import com.example.hksalarycalculatorbackend.model.Rule;
import com.example.hksalarycalculatorbackend.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping
    public ResponseEntity<List<Rule>> getAllRules() {
        List<Rule> rules = ruleService.getAllRules();
        return ResponseEntity.ok(rules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rule> getRuleById(@PathVariable Long id) {
        Rule rule = ruleService.getRuleById(id);
        return ResponseEntity.ok(rule);
    }

    @PostMapping("/create")
    public ResponseEntity<Rule> createRule(@RequestBody RuleDTO ruleDTO) {
        try {
            System.out.println("Incoming RuleDTO: " + ruleDTO);
            Rule createdRule = ruleService.createRule(ruleDTO);
            return ResponseEntity.ok(createdRule);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRule(@PathVariable Long id, @RequestBody RuleDTO ruleDTO, @RequestParam String username) {
        try {
            Rule updatedRule = ruleService.updateRule(id, ruleDTO, username);
            return ResponseEntity.ok("Rule updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating rule: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        ruleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
