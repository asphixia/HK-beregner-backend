package com.example.hksalarycalculatorbackend.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Employee {
    private HKMemberRole hkMemberRole;
    private double hoursWorked;
    private double averageWeeklyHours;
    private boolean overtimeEligible;
    private double calculatedSalary;
    private double hourlyRate;
    private double overtimeHours;
    private double holidayReductionHours;
    private boolean weekendWorkAllowed;
    private boolean flexiblePartTime;
    private int maxWeeklyHours;
    private int maxDailyHours;
    private int age;
    private int seniority;
    private boolean hasEducation;
    private String workTimeStart;
    private String workTimeEnd;
    private double weekendHours;
    private double nightHours;
    private double shiftAllowance;
    private boolean student;
    private double weekendAllowance;
    private double overtimeAllowance;
}
