package br.com.caju.authorizer.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MccEnum {

    FOOD,
    MEAL,
    CASH;

    @JsonCreator
    public static MccEnum fromCode(String code) {
        return switch (code) {
            case "5411", "5412" -> FOOD;
            case "5811", "5812" -> MEAL;
            default -> CASH;
        };
    }
}

