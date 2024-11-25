package com.example.hksalarycalculatorbackend.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Employee {
    private HKMemberRole hkMemberRole;
    private double hoursWorked;
    private double averageWeeklyHours;

    private boolean overtimeEligible;

}
