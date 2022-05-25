package com.company.atm_machine.repository;

import com.company.atm_machine.entity.UserRole;
import com.company.atm_machine.entity.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole,Integer> {
    Optional<UserRole> findByRoles(Roles roles);
}
