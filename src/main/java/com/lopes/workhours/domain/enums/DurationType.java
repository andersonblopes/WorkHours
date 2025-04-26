package com.lopes.workhours.domain.enums;

import lombok.Getter;

@Getter
public enum DurationType {

    MINUTES_15("15 Minutes"),
    MINUTES_30("30 Minutes"),
    MINUTES_45("45 Minutes"),
    ONE_HOUR("One Hour"),
    TWO_HOURS("Two Hours");

    private final String description;

    DurationType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
