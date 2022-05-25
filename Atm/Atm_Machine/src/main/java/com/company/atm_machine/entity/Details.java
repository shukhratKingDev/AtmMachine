package com.company.atm_machine.entity;

import com.company.atm_machine.entity.enums.ActionType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Details {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private ActionTypes actionTypes;
    @Column(nullable = false)
    private Timestamp actionDate;
    @ManyToMany(mappedBy = "detailsList")
    private List<Currency> currency;
    @ManyToOne
    private Atm atm;
}
