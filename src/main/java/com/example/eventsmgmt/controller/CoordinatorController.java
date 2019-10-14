package com.example.eventsmgmt.controller;

import java.util.HashSet;
import java.util.Set;

import com.example.eventsmgmt.model.Role;
import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.model.UserDetails;
import com.example.eventsmgmt.model.payload.ApiResponse;
import com.example.eventsmgmt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CoordinatorController
 */

@RestController
@RequestMapping("api/co-ordinator")
public class CoordinatorController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CoordinatorController coordinatorController;

    public ResponseEntity<?> addRegistrar(UserDetails user) {
        if(userRepository.existsById(user.getId()))
            return new ResponseEntity<Object>(new ApiResponse(false,"Id already exists!"),HttpStatus.BAD_REQUEST);
        if(userRepository.existsByUsername(user.getUsername()))
            return new ResponseEntity<Object>(new ApiResponse(false,"Username already exists!"),HttpStatus.BAD_REQUEST);
        if(userRepository.existsByEmail(user.getEmail()))
            return new ResponseEntity<Object>(new ApiResponse(false,"Email already exists!"),HttpStatus.BAD_REQUEST);
        User newUser = new User(user.getUsername(),user.getPassword(),user.getEmail(),user.getFname(),user.getMname(),user.getLname(),user.getCont());
        Set<Role> role = new HashSet<>();
        coordinatorController.addRegistrar(user);
        return null;
    }
}