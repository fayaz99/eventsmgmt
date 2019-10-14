package com.example.eventsmgmt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.eventsmgmt.model.Event;
import com.example.eventsmgmt.model.User;
import com.example.eventsmgmt.model.UserDetails;
import com.example.eventsmgmt.model.payload.ApiResponse;
import com.example.eventsmgmt.repository.EventRepository;
import com.example.eventsmgmt.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AdminController
 */
@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EventRepository eventRepository;

    @PostMapping(value = "/admins/add")
    public ResponseEntity<?> addAdmin(@Valid @RequestBody User user) {

        if (adminService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Username already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        if (adminService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Email already exists"), HttpStatus.BAD_REQUEST);
        }

        User addUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getEmail(),
                user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getMobile());
        adminService.addAdmin(addUser);

        return new ResponseEntity<Object>(new ApiResponse(true, "Admin Added Successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/admins/{id}")
    public User getAdminById(@PathVariable("id") int id) {
        return adminService.getById(id);
    }

    @GetMapping(value = "/admins/{username}")
    public User getAdminByUsername(@PathVariable("username") String username) {
        return adminService.getByUsername(username);
    }

    @GetMapping(value = "/admins/")
    public List<User> getAdmins() {
        return adminService.getAllAdmins();
    }

    @DeleteMapping(value = "/admins/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable("id") int id) {
        if (!adminService.existsById(id)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Id doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        adminService.deleteById(id);
        return new ResponseEntity<Object>(new ApiResponse(true, "Admin Deleted Successfully"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/admins/{id}")
    public ResponseEntity<?> updateAdminById(@PathVariable("id") int id, @Valid @RequestBody User user) {
        if (!adminService.existsById(id)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Id doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        adminService.updateAdminById(id, user);
        return new ResponseEntity<Object>(new ApiResponse(true, "Admin Updated Successfully"), HttpStatus.BAD_REQUEST);
    }

    /* Co-ordinators */

    @PostMapping("/co-ordinators/add")
    public ResponseEntity<?> addCoordinator(@RequestBody UserDetails user) {

        if (adminService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Username already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        if (adminService.existsByEmail(user.getEmail())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Email already exists"), HttpStatus.BAD_REQUEST);
        }

        adminService.addCoordinator(user);

        return new ResponseEntity<Object>(new ApiResponse(true, "Co-ordinator Added Successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/co-ordinators/{id}")
    public User getCoordinatorById(@PathVariable("id") int id) {
        return adminService.getById(id);
    }

    @GetMapping(value = "/co-ordinators/{username}")
    public User getCoordinatorByUsername(@PathVariable("username") String username) {
        return adminService.getByUsername(username);
    }

    public List<User> getAllCoordinators() {
        return adminService.getAllCoordinators();
    }

    @PutMapping(value = "/co-ordinators/{id}")
    public ResponseEntity<?> updateCoordinatorById(@PathVariable("id") int id, @Valid @RequestBody User user) {
        if (!adminService.existsById(id)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Id doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        adminService.updateCoordinatorById(id, user);
        return new ResponseEntity<Object>(new ApiResponse(true, "Co-ordinator Updated Successfully"),
                HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/co-ordinator/{id}")
    public ResponseEntity<?> deleteCoordinator(@PathVariable("id") int id) {
        if (!adminService.existsById(id)) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Id doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        adminService.deleteById(id);
        return new ResponseEntity<Object>(new ApiResponse(true, "Co-ordinator Deleted Successfully"),
                HttpStatus.BAD_REQUEST);
    }

    /* Events */

    @PostMapping("/{id}/events/add")
    public ResponseEntity<?> addEvent(@RequestBody Event event, @PathVariable int id) {
        if (eventRepository.existsByName(event.getName())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Event already already exists"),
                    HttpStatus.BAD_REQUEST);
        }
        if (eventRepository.existsById(event.getId())) {
            return new ResponseEntity<Object>(new ApiResponse(false, "Event already exists"), HttpStatus.BAD_REQUEST);
        }
        adminService.addEvent(event, id);
        return new ResponseEntity<Object>(new ApiResponse(true, "Event added successfully!!"), HttpStatus.OK);
    }

    @GetMapping("/events/{eventId}")
    public Optional<Event> getEventById(@PathVariable int eventId) {
        return adminService.getEventById(eventId);
    }

    @GetMapping("/events/all")
    public List<Event> getAllEvents() {
        return adminService.getAllEvents();
    }

    @PutMapping("/{id}/events/{eventId}")
    public ResponseEntity<?> updateEventById(@PathVariable int eventId,@RequestBody Event event,@PathVariable int id) {
        if(!eventRepository.existsById(eventId))
            return new ResponseEntity<Object>(new ApiResponse(false, "Event does not exixts!"),HttpStatus.BAD_REQUEST);
        Event newEvent = new Event();
        newEvent.setId(eventId);
        newEvent.setName(event.getName());
        newEvent.setDescription(event.getDescription());
        newEvent.setCreatedAt(event.getCreatedAt());
        newEvent.setCreatedBy(event.getCreatedBy());
        newEvent.setId(id);
        adminService.updateEventById(newEvent);
        return new ResponseEntity<Object>(new ApiResponse(true,"Event updated suucessfully!"),HttpStatus.OK);
    }

    @PutMapping("/{id}/events/{eventName}")
    public ResponseEntity<?> updateEventByName(@PathVariable String eventName,@RequestBody Event event,@PathVariable int id) {
        if(!eventRepository.existsByName(eventName))
            return new ResponseEntity<Object>(new ApiResponse(false, "Event does not exixts!"),HttpStatus.BAD_REQUEST);
        Event newEvent = new Event();
        newEvent.setId(event.getId());
        newEvent.setName(event.getName());
        newEvent.setDescription(event.getDescription());
        newEvent.setCreatedAt(event.getCreatedAt());
        newEvent.setCreatedBy(event.getCreatedBy());
        newEvent.setId(id);
        adminService.updateEventById(newEvent);
        return new ResponseEntity<Object>(new ApiResponse(true,"Event updated suucessfully!"),HttpStatus.OK);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable int id)
    {
        if(!eventRepository.existsById(id))
            return new ResponseEntity<>(new ApiResponse(false, "Event doesn't exist!"),HttpStatus.OK);
        adminService.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, "Event deleted successfully!"),HttpStatus.OK);
    }

    @DeleteMapping("/events/{name}")
    public ResponseEntity<?> deleteEventByName(@PathVariable String name) {
        if(!eventRepository.existsByName(name))
            return new ResponseEntity<>(new ApiResponse(false, "Event doesn't exist!"),HttpStatus.OK);
        adminService.deleteEventByName(name);
        return new ResponseEntity<>(new ApiResponse(true, "Event deleted successfully!"),HttpStatus.OK);
    }
}