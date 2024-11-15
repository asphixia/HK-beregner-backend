package com.example.hksalarycalculatorbackend.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Plan {
    private int period; // e.g., 16 weeks
    private double totalHours;
}
