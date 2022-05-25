package com.company.atm_machine.repository;

import com.company.atm_machine.entity.CardTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardTypesRepository extends JpaRepository<CardTypes,Integer> {
//    Optional<CardTypes> findById(Integer id);
}
