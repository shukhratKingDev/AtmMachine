package com.company.atm_machine.service;

import com.company.atm_machine.payload.LoginDto;
import com.company.atm_machine.payload.Response;
import com.company.atm_machine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;


@Autowired
    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;

}

    public Response login(LoginDto loginDto){
        if (!userRepository.findByEmail(loginDto.getUsername()).isPresent()) {
            return new Response("The user with this username not found",false);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            return new Response("You successfully logged in",true);

        }catch (BadCredentialsException e){
            return new Response("Incorrect username or password",false);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("This user not found"));
    }
}
