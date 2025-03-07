package com.Project.UserAuthentication.repository;

import com.Project.UserAuthentication.domain.UserAuthentication;
import com.Project.UserAuthentication.exception.InvalidCredentialsException;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAuthenticationRepository extends JpaRepository<UserAuthentication,String> {
    UserAuthentication findByEmailAndPassword(String email,String password);

    UserAuthentication findByEmail(String email);

}
