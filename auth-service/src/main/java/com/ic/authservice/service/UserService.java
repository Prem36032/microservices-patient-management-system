package com.ic.authservice.service;

import com.ic.authservice.model.User;
import com.ic.authservice.repository.userRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final userRepository repository;

    public UserService(userRepository repository){
        this.repository = repository;
    }

    public Optional<User>  getUserByEmail(String email){
        return repository.findByEmail(email);
    }

}
