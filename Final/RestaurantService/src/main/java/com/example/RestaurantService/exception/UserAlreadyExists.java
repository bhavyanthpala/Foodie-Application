package com.example.RestaurantService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "User Already Exists")
public class UserAlreadyExists extends Exception{
}
