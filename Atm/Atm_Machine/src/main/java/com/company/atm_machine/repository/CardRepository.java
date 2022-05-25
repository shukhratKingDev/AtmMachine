package com.company.atm_machine.repository;

import com.company.atm_machine.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CardRepository  extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumber(String cardNumber);
}
