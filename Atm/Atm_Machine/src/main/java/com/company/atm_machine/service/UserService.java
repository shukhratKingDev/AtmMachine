package com.company.atm_machine.service;

import com.company.atm_machine.entity.Atm;
import com.company.atm_machine.entity.Card;
import com.company.atm_machine.entity.Currency;
import com.company.atm_machine.entity.Details;
import com.company.atm_machine.entity.enums.CardType;
import com.company.atm_machine.payload.BalanceDto;
import com.company.atm_machine.payload.Response;
import com.company.atm_machine.repository.AtmRepository;
import com.company.atm_machine.repository.CardRepository;
import com.company.atm_machine.repository.DetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private CardRepository cardRepository;
    private AtmRepository atmRepository;
    private PasswordEncoder passwordEncoder;
    private EmployeeService employeeService;
    private DetailsRepository detailsRepository;

    public UserService(CardRepository cardRepository, AtmRepository atmRepository, PasswordEncoder passwordEncoder, EmployeeService employeeService, DetailsRepository detailsRepository) {
        this.cardRepository = cardRepository;
        this.atmRepository = atmRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeService = employeeService;
        this.detailsRepository = detailsRepository;
    }


    public Response getMoney(String cardNumber, BalanceDto balanceDto){
        Optional<Card> optionalCard= cardRepository.findByCardNumber(cardNumber);
        if (!optionalCard.isPresent()) {
            return new Response("Card not found",false);
        }
        if (!optionalCard.get().getPassword().equals(passwordEncoder.encode(balanceDto.getCardPassword()))) {
          return new Response("Incorrect password",false);
        }
        if (optionalCard.get().getExpirationDate().compareTo(Timestamp.valueOf(LocalDateTime.now()))!=0) {
            return new Response("Card availability expired",false);
        }
        Optional<Atm> optionalAtm = atmRepository.findById(balanceDto.getAtmId());
        if (optionalAtm.isPresent()) {
            return new Response("The Atm with this id not found",false);
        }

        double amount=balanceDto.getAmount();

        if (optionalAtm.get().getBank().equals(optionalAtm.get().getBank())) {
            amount+=balanceDto.getAmount()*optionalAtm.get().getCommissionForTheSameBankCards();
        }else{
            amount+=balanceDto.getAmount()*optionalAtm.get().getCommissionForNonTheSameBankCards();

        }

        if (!(optionalCard.get().getBalance()>=amount)) {
            return new Response("You do not have enough money",false);

        }

        if (optionalAtm.get().getMaxAmountOfMoneyToGet()<=balanceDto.getAmount()) {
            return new Response("You can not get this amount at once. Because max amount of money to get is"+optionalAtm.get().getMaxAmountOfMoneyToGet()+".",false);
        }
        int i=0;
        if (optionalCard.get().getCardTypes().equals(CardType.HUMO)||optionalCard.get().getCardTypes().equals(CardType.UZCARD)) {
            if (!(optionalAtm.get().getCurrentAmount()>=balanceDto.getAmount())){
                return new Response("Atm does not have enough money. Soon, atm will be balanced",false);

            }
        }
        if (optionalCard.get().getCardTypes().equals(CardType.VISA)||optionalCard.get().getCardTypes().equals(CardType.MASTERCARD)) {
            i=1;
            if (!(optionalAtm.get().getCurrentAmount()>=balanceDto.getAmount())){
                employeeService.sendEmail(optionalAtm.get().getUsers().getEmail());
                return new Response("Atm does not have enough money. Soon, atm will be balanced",false);
            }
        }




        long money=(long)balanceDto.getAmount();

int []currencyAmount={1,5,10,20,50,100};
        int []currencyAtm={};
int counter=0;
        double remainder=balanceDto.getAmount();
        while(remainder!=0||remainder<1){
            int number=(int)money/currencyAmount[counter];
            if (number>0) {
                money-= (long) number *currencyAmount[counter];
                remainder-=money;
                currencyAtm[counter]=number;
                counter++;
            }
        }
        if (remainder!=0) {
            return new Response("Atm can not give you this amount, please enter another one",false);
        }
        optionalAtm.get().setCurrentAmount(optionalAtm.get().getCurrentAmount()-money);
            optionalAtm.get().getListOfCurrencies().get(i).setNumberOf100(optionalAtm.get().getListOfCurrencies().get(i).getNumberOf100()-currencyAtm[0]);
            optionalAtm.get().getListOfCurrencies().get(i).setNumberOf50(optionalAtm.get().getListOfCurrencies().get(i).getNumberOf50()-currencyAtm[1]);
            optionalAtm.get().getListOfCurrencies().get(i).setNumberOf20(optionalAtm.get().getListOfCurrencies().get(i).getNumberOf20()-currencyAtm[2]);
            optionalAtm.get().getListOfCurrencies().get(i).setNumberOf10(optionalAtm.get().getListOfCurrencies().get(i).getNumberOf10()-currencyAtm[3]);
            optionalAtm.get().getListOfCurrencies().get(i).setNumberOf5(optionalAtm.get().getListOfCurrencies().get(i).getNumberOf5()-currencyAtm[4]);
            optionalAtm.get().getListOfCurrencies().get(i).setNumberOf1(optionalAtm.get().getListOfCurrencies().get(i).getNumberOf1()-currencyAtm[5]);
            optionalCard.get().setBalance(amount);

        Details details=new Details();
        details.setActionDate(Timestamp.valueOf(LocalDateTime.now()));
        Currency currency=new Currency();
        currency.setCurrencyTypes(optionalAtm.get().getListOfCurrencies().get(i).getCurrencyTypes());
        currency.setNumberOf1(currencyAmount[0]);
        currency.setNumberOf5(currencyAmount[1]);
        currency.setNumberOf10(currencyAmount[2]);
        currency.setNumberOf20(currencyAmount[3]);
        currency.setNumberOf50(currencyAmount[4]);
        currency.setNumberOf100(currencyAmount[5]);
        details.setCurrency(List.of(currency));
        details.setAtm(optionalAtm.get());
        detailsRepository.save(details);
            atmRepository.save(optionalAtm.get());
            cardRepository.save(optionalCard.get());
            if (optionalAtm.get().getMinimumAmountOfMoneyToBalanced()>optionalAtm.get().getCurrentAmount()){
                employeeService.sendEmail(optionalAtm.get().getUsers().getEmail());
            }
            return new Response("Transaction has been successfully",true);
    }
}
