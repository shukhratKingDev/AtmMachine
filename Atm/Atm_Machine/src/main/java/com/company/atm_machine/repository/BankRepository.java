package com.company.atm_machine.repository;

import com.company.atm_machine.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "bank",collectionResourceRel = "listOfBanks")
public interface BankRepository extends JpaRepository<Bank,Integer> {
}
