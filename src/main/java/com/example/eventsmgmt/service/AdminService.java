package com.example.eventsmgmt.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import com.example.eventsmgmt.model.Event;
import com.example.eventsmgmt.model.Role;
import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.model.UserDetails;
import com.example.eventsmgmt.repository.EventRepository;
import com.example.eventsmgmt.repository.RoleRepository;
import com.example.eventsmgmt.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserService
 */
@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EventRepository eventRepository;

    public void addAdmin(User user) {

        // Set admin role
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found")));
        user.setRole(roles);

        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found By Id"));
    }

    public List<User> getAllAdmins() {
        return userRepository.findAll();
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found by username"));
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public void updateAdminById(int id, User user) {
        User oldDetails = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find user"));
        user.setId(id);
        // Created at will always be same
        user.setCreatedAt(oldDetails.getCreatedAt());
        // Change the last updated details to now
        Date date = new Date();
        user.setLastUpdated(new Timestamp(date.getTime()));
        // Check if admin has updated password
        String password = user.getPassword();
        Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        if (!BCRYPT_PATTERN.matcher(password).matches() && password != null && password.length() > 0) {
            // If updated password then change the password
            password = passwordEncoder.encode(password);
            user.setPassword(password);
        } else {
            // Set the password to old password If password not changed
            user.setPassword(oldDetails.getPassword());
        }
        // Roles will always be the same
        user.setRole(oldDetails.getRole());
        userRepository.save(user);
    }

    /* Co-ordinators */

    public void addCoordinator(UserDetails newUser) {

        // Set co-ordinator role
        User user = new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()), newUser.getEmail(),
                newUser.getFname(), newUser.getMname(), newUser.getLname(), newUser.getCont());
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName("CO-ORDINATOR").orElseThrow(() -> new RuntimeException("Role not found")));
        user.setRole(roles);

        //Set co-ordinator event
        user.setEvent(((Optional<Event>) eventRepository.findByName(newUser.getEventName()))
                .orElseThrow(() -> new RuntimeException("Event not found")));

        userRepository.save(user);
    }

    public List<User> getAllCoordinators() {
        return userRepository.findByRoleName("CO-ORDINATOR");
    }

    public void updateCoordinatorById(int id, User user) {
        User oldDetails = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find user"));
        user.setId(id);
        // Created at will always be same
        user.setCreatedAt(oldDetails.getCreatedAt());
        // Change the last updated details to now
        Date date = new Date();
        user.setLastUpdated(new Timestamp(date.getTime()));
        // Check if co-ordinator has updated password
        String password = user.getPassword();
        Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        if (!BCRYPT_PATTERN.matcher(password).matches() && password != null && password.length() > 0) {
            // If updated password then change the password
            password = passwordEncoder.encode(password);
            user.setPassword(password);
        } else {
            // Set the password to old password If password not changed
            user.setPassword(oldDetails.getPassword());
        }
        // Roles will always be the same
        user.setRole(oldDetails.getRole());
        user.setEvent(oldDetails.getEvent());
        userRepository.save(user);
    }

    /* Events */

    public void addEvent(Event event,int id) {
        Date date = new Date();
        event.setCreatedAt(new Timestamp(date.getTime()));
        event.setCreatedBy(id);
        event.setUpdatedAt(new Timestamp(date.getTime()));
        event.setUpdatedBy(id);
        eventRepository.save(event);
    }

    public Optional<Event> getEventById(int id) {
        return eventRepository.findById(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void updateEventById(Event newEvent) {
        newEvent.setUpdatedAt(new Timestamp((new Date()).getTime()));
        eventRepository.save(newEvent);
    }

    public void updateEventByName(Event newEvent) {
        newEvent.setUpdatedAt(new Timestamp((new Date()).getTime()));
        eventRepository.save(newEvent);
    }

    public void deleteEventById(int id) {
        eventRepository.deleteById(id);
    }

    public void deleteEventByName(String name) {
        eventRepository.deleteByName(name);;
    }
}