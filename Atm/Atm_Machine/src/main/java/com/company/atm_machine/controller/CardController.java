package com.company.atm_machine.controller;

import com.company.atm_machine.entity.Details;
import com.company.atm_machine.payload.CardDto;
import com.company.atm_machine.payload.DetailsDto;
import com.company.atm_machine.payload.EmployeeDto;
import com.company.atm_machine.payload.Response;
import com.company.atm_machine.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {
    private CardService cardService;
@Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/addCard")
    public ResponseEntity<Response> registerWorker(@RequestBody CardDto cardDto){
        Response response=cardService.addCard(cardDto);
        return ResponseEntity.status(response.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(response);
    }

}
