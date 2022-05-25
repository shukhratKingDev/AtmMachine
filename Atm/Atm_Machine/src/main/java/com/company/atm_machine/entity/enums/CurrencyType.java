package com.company.atm_machine.entity.enums;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum CurrencyType {
    USD("usd"),
    SUM("sum");
    private String shortName;

    CurrencyType(String shortName) {
        this.shortName = shortName;
    }
}
