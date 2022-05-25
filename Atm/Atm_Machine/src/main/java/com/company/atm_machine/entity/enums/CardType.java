package com.company.atm_machine.entity.enums;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum CardType {
    VISA(CurrencyType.USD),
    HUMO(CurrencyType.SUM),
    UZCARD(CurrencyType.SUM),
    MASTERCARD(CurrencyType.USD);

    private CurrencyType currencyType;
    CardType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }
}
