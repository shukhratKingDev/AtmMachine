package com.company.atm_machine.repository;

import com.company.atm_machine.entity.Atm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  AtmRepository extends JpaRepository<Atm,Integer> {
}
