package com.company.atm_machine.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.security.DenyAll;

@Data
@AllArgsConstructor
public class Response {
    private String message;
    private boolean success;
}
