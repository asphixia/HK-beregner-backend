package com.example.hksalarycalculatorbackend.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Agreement {
    private double normalWorkHours;
    private String workInterval;
}
