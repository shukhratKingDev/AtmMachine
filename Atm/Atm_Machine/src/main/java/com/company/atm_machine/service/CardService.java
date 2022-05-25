package com.company.atm_machine.service;

import com.company.atm_machine.entity.Bank;
import com.company.atm_machine.entity.Card;
import com.company.atm_machine.entity.CardTypes;
import com.company.atm_machine.payload.CardDto;
import com.company.atm_machine.payload.Response;
import com.company.atm_machine.repository.BankRepository;
import com.company.atm_machine.repository.CardRepository;
import com.company.atm_machine.repository.CardTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CardService {
    private BankRepository bankRepository;
    private CardRepository cardRepository;
    private EmployeeService employeeService;
    private CardTypesRepository cardTypesRepository;
    private PasswordEncoder passwordEncoder;
@Autowired
    public CardService(BankRepository bankRepository, CardRepository cardRepository, EmployeeService employeeService, CardTypesRepository cardTypesRepository, PasswordEncoder passwordEncoder) {
        this.bankRepository = bankRepository;
        this.cardRepository = cardRepository;
    this.employeeService = employeeService;
    this.cardTypesRepository = cardTypesRepository;
    this.passwordEncoder = passwordEncoder;
}

    public Response addCard(CardDto cardDto){
        if (employeeService.checkBank(cardDto.getBankId()).isSuccess()) {
            return new Response("The bank with this id not found",false);
        }
        Optional<Bank> optionalBank = bankRepository.findById(cardDto.getBankId());
        Card card=new Card();
        card.setCvv(cardDto.getCvv());
        cardDto.setPassword(passwordEncoder.encode(cardDto.getPassword()));
        cardDto.setFirstName(cardDto.getFirstName());
        cardDto.setLastName(cardDto.getLastName());
        card.setBank(optionalBank.get());
        Optional<CardTypes> cardTypes = cardTypesRepository.findById(cardDto.getCardTypeId());
        if (!cardTypes.isPresent()) {
           return new Response("Incorrect cardId",false) ;
        }
        card.setCardTypes(cardTypes.get());
        card.setBalance(0);
        card.setExpirationDate(Timestamp.valueOf(LocalDateTime.now().plusYears(4)));
        String cardNumber=System.currentTimeMillis()+""+(System.currentTimeMillis()+"").substring(10,13);
card.setCardNumber(cardNumber);
cardRepository.save(card);
return new Response("Card saved successfully",true);
    }
}
