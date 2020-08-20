package com.mc855.tracker.domain.reference;

import lombok.Getter;

@Getter
public enum HealthState {
    POSITIVE,
    NEGATIVE,
    SYMPTOMATIC,
    POSSIBLY_INFECTED,
    ASYMPTOMATIC,
    CURED;
}
