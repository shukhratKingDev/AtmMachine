package com.company.atm_machine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private long numberOf1;
    private long numberOf5;
    private long numberOf10;
    private long numberOf20;
    private long numberOf50;
    private long numberOf100;
    @ManyToOne
    private CurrencyTypes currencyTypes;
    @ManyToMany(mappedBy = "listOfCurrencies")
    private List<Atm> atm_currencies;
    @ManyToMany
    private List<Details> detailsList;

    public Currency(long numberOf1, long numberOf5, long numberOf10, long numberOf20, long numberOf50, long numberOf100, CurrencyTypes currencyTypes) {
        this.numberOf1 = numberOf1;
        this.numberOf5 = numberOf5;
        this.numberOf10 = numberOf10;
        this.numberOf20 = numberOf20;
        this.numberOf50 = numberOf50;
        this.numberOf100 = numberOf100;
        this.currencyTypes = currencyTypes;
    }
}
