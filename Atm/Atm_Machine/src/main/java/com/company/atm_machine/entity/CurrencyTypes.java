package com.company.atm_machine.entity;

import com.company.atm_machine.entity.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CurrencyTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    public CurrencyTypes(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }
}
