package com.company.atm_machine.repository;

import com.company.atm_machine.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailsRepository extends JpaRepository<Details,Integer> {
    List<Details> findAllByAtm_Bank_IdAndAtm_Id(Integer atm_bank_id, Long atm_id);
}
