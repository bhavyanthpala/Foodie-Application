package com.Project.UserAuthentication.controller;

import com.Project.UserAuthentication.domain.UserAuthentication;
import com.Project.UserAuthentication.exception.InvalidCredentialsException;
import com.Project.UserAuthentication.exception.UserAlreadyExistsException;
import com.Project.UserAuthentication.security.ISecurityTokenGenerator;
import com.Project.UserAuthentication.service.IUserAuthenticationService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class UserAuthenticationController{

    private IUserAuthenticationService iUserAuthenticationService;
    private ISecurityTokenGenerator iSecurityTokenGenerator;
    private ResponseEntity responseEntity;
    @Autowired
    public UserAuthenticationController(IUserAuthenticationService iUserAuthenticationService, ISecurityTokenGenerator iSecurityTokenGenerator) {
        this.iUserAuthenticationService = iUserAuthenticationService;
        this.iSecurityTokenGenerator = iSecurityTokenGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity saveUser(@RequestBody UserAuthentication userAuthentication) throws UserAlreadyExistsException {
            UserAuthentication userAuthentication1=iUserAuthenticationService.registerUser(userAuthentication);
            responseEntity=new ResponseEntity(userAuthentication1, HttpStatus.CREATED);
            return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserAuthentication userAuthentication) throws InvalidCredentialsException {
        System.out.println("Inside Controller Layer");
        System.out.println(userAuthentication);
            UserAuthentication userAuthentication1=iUserAuthenticationService.login(userAuthentication.getEmail(), userAuthentication.getPassword());
        System.out.println("calling Create token");
            Map<String,String> map=iSecurityTokenGenerator.createToken(userAuthentication1);
        System.out.println("Map: "+ map);
            responseEntity=new ResponseEntity(map,HttpStatus.OK);
            return responseEntity;
    }

}
