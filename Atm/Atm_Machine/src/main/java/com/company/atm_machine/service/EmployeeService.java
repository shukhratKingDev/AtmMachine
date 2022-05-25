package com.company.atm_machine.service;

import com.company.atm_machine.entity.*;
import com.company.atm_machine.entity.enums.Roles;
import com.company.atm_machine.payload.DetailsDto;
import com.company.atm_machine.payload.EmployeeDto;
import com.company.atm_machine.payload.Response;
import com.company.atm_machine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EmployeeService {
    private BankRepository bankRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JavaMailSender javaMailSender;
    private DetailsRepository detailsRepository;
    private AtmRepository atmRepository;
@Autowired
    public EmployeeService(BankRepository bankRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authentication, JavaMailSender javaMailSender, DetailsRepository detailsRepository, AtmRepository atmRepository) {
    this.bankRepository = bankRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
    this.authenticationManager = authentication;
    this.javaMailSender = javaMailSender;
    this.detailsRepository = detailsRepository;
    this.atmRepository = atmRepository;
}

    public Response addDirector(EmployeeDto employeeDto){
        if (!checkBank(employeeDto.getBankId()).isSuccess()) {
            return checkBank(employeeDto.getBankId());
        }
        if (!emailCheck(employeeDto.getEmail()).isSuccess()) {
            return emailCheck(employeeDto.getEmail());
        }
        UserRole role=roleRepository.findByRoles(Roles.DIRECTOR).get();
        User director=parseFields(employeeDto,role);
        userRepository.save(director);
        return new Response("Director saved successfully",true);
    }

    public Response addWorker(EmployeeDto employeeDto){
        if (!checkBank(employeeDto.getBankId()).isSuccess()) {
            return new Response("Bank with this id not found",false);
        }
        if (emailCheck(employeeDto.getEmail()).isSuccess()) {
            return new Response("This email already exists",false);
        }
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        UserRole userRole=roleRepository.findByRoles(Roles.WORKER).get();
        employeeDto.setBankId(user.getBank().getId());
        User employee=parseFields(employeeDto,userRole);
        userRepository.save(employee);
        return new Response("Employee Successfully added",true);
    }




    public Response checkBank(Integer id){
        Optional<Bank> optionalBank= bankRepository.findById(id);
        if (!optionalBank.isPresent()) {
            return new Response("Bank with this id not found",false);
        }
        return new Response("Success",true);
    }

    public Response emailCheck(String email){
        if (userRepository.findByEmail(email).isPresent()) {
            return new Response("This user already exists",false);
        }
        return new Response("success",true);
    }
    public User parseFields(EmployeeDto employeeDto, UserRole userRole){
    User user=new User();
    user.setFirstName(employeeDto.getFirstName());
    user.setLastName(employeeDto.getLastName());
    user.setEmail(employeeDto.getEmail());
    user.setEnabled(true);
    user.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
    Bank bank= bankRepository.findById(employeeDto.getBankId()).get();
    user.setBank(bank);
    user.setUserRole(List.of(userRole));
      return user;
    }
    public Boolean sendEmail(String sending_email){
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("abs@gmail.com");
            message.setTo(sending_email);
            message.setSubject("Atm balance less than enough");
            message.setText("<a href='http://localhost:8080/api/balanceAtm'>Balance Atm</a>");
            javaMailSender.send(message);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Details> getListOfActions(DetailsDto detailsDto){
        if (!checkBank(detailsDto.getBankId()).isSuccess()) {
            return null;
        }

        Optional<Bank> optionalBank = bankRepository.findById(detailsDto.getBankId());
        Optional<Atm> optionalAtm = atmRepository.findById(Math.toIntExact(detailsDto.getAtmId()));
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User director=(User)authentication.getPrincipal();
        if (!(director.getUserRole().equals(Roles.DIRECTOR)&& director.getBank().equals(optionalBank.get()))) {
          return null;
        }
        List<Details> atmForDetails = detailsRepository.findAllByAtm_Bank_IdAndAtm_Id(detailsDto.getBankId(), detailsDto.getAtmId());
        return new ArrayList<>(atmForDetails);
    }

}

