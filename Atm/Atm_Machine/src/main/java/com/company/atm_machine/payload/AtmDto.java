package com.company.atm_machine.payload;

import com.company.atm_machine.entity.enums.CardType;
import lombok.Data;

import java.util.List;

@Data
public class AtmDto {
    private List<String> cardTypes;
    private double maxAmountOfMoney;
    private double commissionSame;
    private double commissionNonSame;
    private double minimumMoney;
    private String address;
    private String username;
    private List<String>listOfCurrencies;
}
