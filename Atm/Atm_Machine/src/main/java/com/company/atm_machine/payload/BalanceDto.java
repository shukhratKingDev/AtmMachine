package com.company.atm_machine.payload;

import lombok.Data;

@Data
public class BalanceDto {
    private String cardPassword;
    private double amount;
    private Integer atmId;
}
