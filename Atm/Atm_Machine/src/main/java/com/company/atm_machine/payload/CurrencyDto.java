package com.company.atm_machine.payload;

import lombok.Data;

@Data
public class CurrencyDto {
    private long numberOf1;
    private long numberOf5;
    private long numberOf10;
    private long numberOf20;
    private long numberOf50;
    private long numberOf100;
    private String currencyType;
}
