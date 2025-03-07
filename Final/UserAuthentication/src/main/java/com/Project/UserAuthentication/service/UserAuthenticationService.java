package com.Project.UserAuthentication.service;

import com.Project.UserAuthentication.domain.UserAuthentication;
import com.Project.UserAuthentication.exception.InvalidCredentialsException;
import com.Project.UserAuthentication.exception.UserAlreadyExistsException;
import com.Project.UserAuthentication.repository.IUserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthenticationService implements IUserAuthenticationService{

    private final IUserAuthenticationRepository iUserAuthenticationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticationService(IUserAuthenticationRepository iUserAuthenticationRepository, PasswordEncoder passwordEncoder) {
        this.iUserAuthenticationRepository = iUserAuthenticationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAuthentication registerUser(UserAuthentication userAuthentication) throws UserAlreadyExistsException {
        System.out.println("Inside Service Layer");
        Optional<UserAuthentication> optionalUser = iUserAuthenticationRepository.findById(userAuthentication.getUserId());
        System.out.println("Optional User: " + optionalUser);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException();
        }
        // Hash the password before saving it
        userAuthentication.setPassword(passwordEncoder.encode(userAuthentication.getPassword()));
        return iUserAuthenticationRepository.save(userAuthentication);
    }

    @Override
    public UserAuthentication login(String email, String password) throws InvalidCredentialsException {
        System.out.println("Inside Service Layer");
        UserAuthentication user = iUserAuthenticationRepository.findByEmail(email);
        if(user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        return user;
    }
}
