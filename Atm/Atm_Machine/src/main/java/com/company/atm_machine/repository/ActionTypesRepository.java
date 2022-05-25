package com.company.atm_machine.repository;

import com.company.atm_machine.entity.ActionTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTypesRepository extends JpaRepository<ActionTypes,Integer> {
}
