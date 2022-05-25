package com.company.atm_machine.payload;

import lombok.Data;

@Data
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int bankId;
}
