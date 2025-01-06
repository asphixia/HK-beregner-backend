package com.example.hksalarycalculatorbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleDTO {
    private Long id;
    private String ruleName;
    private String conditions;
    private String actions;
    private boolean status;

    @Override
    public String toString() {
        return "RuleDTO{" +
                "id=" + id +
                ", ruleName='" + ruleName + '\'' +
                ", conditions='" + conditions + '\'' +
                ", actions='" + actions + '\'' +
                ", status=" + status +
                '}';
    }
}
