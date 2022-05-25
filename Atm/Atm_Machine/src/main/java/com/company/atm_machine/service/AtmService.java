package com.company.atm_machine.service;

import com.company.atm_machine.entity.*;
import com.company.atm_machine.entity.enums.CardType;
import com.company.atm_machine.entity.enums.CurrencyType;
import com.company.atm_machine.entity.enums.Roles;
import com.company.atm_machine.payload.AtmDto;
import com.company.atm_machine.payload.CurrencyDto;
import com.company.atm_machine.payload.Response;
import com.company.atm_machine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AtmService {
    private AtmRepository atmRepository;
    private EmployeeService employeeService;
    private BankRepository bankRepository;
    private UserRepository userRepository;
    private DetailsRepository detailsRepository;
    private ActionTypesRepository actionTypesRepository;
@Autowired
    public AtmService(AtmRepository atmRepository, EmployeeService employeeService, BankRepository bankRepository, UserRepository userRepository, DetailsRepository detailsRepository, ActionTypesRepository actionTypesRepository) {
        this.atmRepository = atmRepository;
    this.employeeService = employeeService;
    this.bankRepository = bankRepository;
    this.userRepository = userRepository;
    this.detailsRepository = detailsRepository;
    this.actionTypesRepository = actionTypesRepository;
}

    public Response addAtm(AtmDto atmDto){
        if (employeeService.emailCheck(atmDto.getUsername()).isSuccess()) {
            return new Response("The user not found",false);
        }

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();
        if (!user.getAuthorities().equals(Roles.DIRECTOR)) {
           return new Response("Atm machines can only be added by company directors",false);
        }

        Optional<User> employee = userRepository.findByEmail(atmDto.getUsername());

        Atm atm=new Atm();
        atm.setBank(user.getBank());
        atm.setUsers(employee.get());
        List<CardTypes> cardTypes=parseCardTypesList(atmDto.getCardTypes());
        atm.setCardTypes(cardTypes);
        atm.setAddress(atmDto.getAddress());
        atm.setMaxAmountOfMoneyToGet(atmDto.getMaxAmountOfMoney());
        atm.setCommissionForTheSameBankCards(atm.getCommissionForTheSameBankCards());
        atm.setCommissionForNonTheSameBankCards(atm.getCommissionForNonTheSameBankCards());
        atm.setMinimumAmountOfMoneyToBalanced(atmDto.getMinimumMoney());
        List<Currency> currencies=new ArrayList<>(List.of(
                new Currency(1,1,1,1,1,1,new CurrencyTypes(CurrencyType.SUM))
        ));
        atm.setListOfCurrencies(currencies);

        atmRepository.save(atm);
        return new Response("Atm successfully saved",true);



    }

    public List<CardTypes> parseCardTypesList(List<String> cards){
    List<CardTypes>listOfCards=new ArrayList<>();
    CardTypes cardTypes=new CardTypes();
        for (String card : cards) {
            switch (card.toUpperCase()) {
                case "UZCARD" -> {cardTypes.setCardType(CardType.UZCARD);
                    listOfCards.add(cardTypes);}
                case "HUMO" -> {cardTypes.setCardType(CardType.HUMO);
                    listOfCards.add(cardTypes);}
                case "VISA" -> {cardTypes.setCardType(CardType.VISA);
                    listOfCards.add(cardTypes);}
                case "MASTERCARD" -> {
                    cardTypes.setCardType(CardType.MASTERCARD);
                    listOfCards.add(cardTypes);}
            }
        }
       return listOfCards;
    }

    public Response balanceAtm(Integer atm_id, CurrencyDto currencyDto){
        Optional<Atm> optionalAtm = atmRepository.findById(atm_id);
Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
User user=(User)authentication.getPrincipal();
user.getAuthorities().equals(Roles.WORKER);
        if (!optionalAtm.get().getUsers().getUsername().equals(user.getUsername())) {
            return new Response("You can not balance this atm",false);
        }
        Currency currency=new Currency();
        CurrencyTypes currencyTypes=new CurrencyTypes();
        if (currencyDto.getCurrencyType().equalsIgnoreCase("usd")) {
            currencyTypes.setCurrencyType(CurrencyType.USD);
        }else
            if(currencyDto.getCurrencyType().equalsIgnoreCase("sum")){
                currencyTypes.setCurrencyType(CurrencyType.SUM);
            }
            else{
                return new Response("Incorrect currency type",false);
            }
            currency.setCurrencyTypes(currencyTypes);

        List<Currency>currencies=new ArrayList<>();
        currencies.add(currency);
        Atm atm=optionalAtm.get();
        atm.setListOfCurrencies(currencies);
        Details details=new Details();
        details.setActionDate(Timestamp.valueOf(LocalDateTime.now()));
        details.setCurrency(currencies);
        details.setActionTypes(actionTypesRepository.findById(1).get());
        details.setAtm(optionalAtm.get());
        detailsRepository.save(details);
        atmRepository.save(atm);
        return new Response("Atm successfully balanced",true);
    }

}
