package com.example.eventsmgmt.repository;

import java.util.Optional;

import com.example.eventsmgmt.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EventRepository
 */
public interface EventRepository extends JpaRepository<Event,Integer> {

    Optional<Event> findByName(String name);

    // Event searchByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);
}