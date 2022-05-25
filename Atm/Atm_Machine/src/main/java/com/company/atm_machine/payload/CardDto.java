package com.company.atm_machine.payload;

import lombok.Data;

@Data
public class CardDto {
    private String cvv;
    private String password;
    private String firstName;
    private String lastName;
    private int bankId;
    private int cardTypeId;
}
