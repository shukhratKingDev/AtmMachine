package com.company.atm_machine.controller;

import com.company.atm_machine.payload.AtmDto;
import com.company.atm_machine.payload.Response;
import com.company.atm_machine.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AtmController {
    private AtmService atmService;
@Autowired
    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }
    @PostMapping("/addAtm")
    public ResponseEntity<Response>addAtm(AtmDto atmDto){
    Response response=atmService.addAtm(atmDto);
    return ResponseEntity.status(response.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/balanceAtm")
    public ResponseEntity<Response>balanceAtm(AtmDto atmDto){
        Response response=atmService.addAtm(atmDto);
        return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(response);
    }
}
