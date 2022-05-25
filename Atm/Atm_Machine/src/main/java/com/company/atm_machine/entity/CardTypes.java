package com.company.atm_machine.entity;

import com.company.atm_machine.entity.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CardTypes {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CardType  cardType;
    @OneToMany(mappedBy = "cardTypes")
    private List<Card> card;

    public CardTypes(CardType cardType) {
        this.cardType = cardType;
    }
}
