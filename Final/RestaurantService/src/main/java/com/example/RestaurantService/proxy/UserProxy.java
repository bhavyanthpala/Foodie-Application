package com.example.RestaurantService.proxy;

import com.example.RestaurantService.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="UserAuthentication",url = "localhost:8086")
public interface UserProxy {

    @PostMapping("/api/v1/register")
    public ResponseEntity saveUser(@RequestBody User user);

}
