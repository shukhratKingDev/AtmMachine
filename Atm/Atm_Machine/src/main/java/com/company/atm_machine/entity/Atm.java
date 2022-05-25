package com.company.atm_machine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Atm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<CardTypes> cardTypes;
    @Column(nullable = false)
    private double maxAmountOfMoneyToGet;
    @Column(nullable = false)
    private double commissionForTheSameBankCards;
    @Column(nullable = false)
    private double commissionForNonTheSameBankCards;
    @Column(nullable = false)
    private double minimumAmountOfMoneyToBalanced;
    private double currentAmount;
    @Column(nullable = false,unique = true)
    private String address;
    @OneToOne
    private User users;
    @ManyToOne
    private Bank bank;
    @ManyToMany
    private List<Currency> listOfCurrencies;
}
