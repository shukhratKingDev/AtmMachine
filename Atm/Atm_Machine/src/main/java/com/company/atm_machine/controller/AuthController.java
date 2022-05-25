package com.company.atm_machine.controller;

import com.company.atm_machine.entity.Details;
import com.company.atm_machine.payload.DetailsDto;
import com.company.atm_machine.payload.EmployeeDto;
import com.company.atm_machine.payload.LoginDto;
import com.company.atm_machine.payload.Response;
import com.company.atm_machine.service.AuthService;
import com.company.atm_machine.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthController{
    private AuthService authService;
    private EmployeeService employeeService;
    public AuthController(AuthService authService, EmployeeService employeeService) {
        this.authService = authService;
        this.employeeService = employeeService;
    }
    @PostMapping("/addDirector")
    public ResponseEntity<Response> registerDirector(@RequestBody EmployeeDto employeeDto){
         Response response=employeeService.addDirector(employeeDto);
         return ResponseEntity.status(response.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(response);
    }
    @PostMapping("/addEmployee")
    public ResponseEntity<Response> registerWorker(@RequestBody EmployeeDto employeeDto){
        Response response=employeeService.addWorker(employeeDto);
        return ResponseEntity.status(response.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(response);
    }
    @GetMapping("/getDetails")
    public ResponseEntity<List<Details>> getListOfActions(DetailsDto detailsDto){
        return ResponseEntity.ok(employeeService.getListOfActions(detailsDto));
    }

    @GetMapping("/auth/login")
    public ResponseEntity<Response> login(LoginDto loginDto){
    Response response=authService.login(loginDto);
    return ResponseEntity.status(response.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(response);
    }
}
