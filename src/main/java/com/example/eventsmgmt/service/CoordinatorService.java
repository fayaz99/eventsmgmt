package com.example.eventsmgmt.service;

import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CoordinatorService
 */
@Service
public class CoordinatorService {

    @Autowired
    UserRepository userRepository;

    public void addRegistrar(User user) {
        userRepository.save(user);
    }    
}