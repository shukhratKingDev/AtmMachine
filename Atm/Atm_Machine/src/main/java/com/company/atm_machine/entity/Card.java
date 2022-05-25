package com.company.atm_machine.entity;

import com.company.atm_machine.entity.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,unique = true,updatable = false)
    private String cardNumber;
    @Column(nullable = false,updatable = false)
    private String cvv;
    @Column(nullable = false)
    private String password;
    private double balance;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @ManyToOne
    private CardTypes cardTypes;
    private Timestamp expirationDate;
    @ManyToOne
    private Bank bank;
}
