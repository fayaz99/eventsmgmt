package com.example.eventsmgmt.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Event
 */
@Entity
public class Event {

    @Id
    @Column(name = "event_id")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "created_at")
    public Timestamp createdAt;

    @Column(name = "created_by")
    public int createdBy;

    @Column(name = "updated_at")
    public Timestamp updatedAt;

    @Column(name = "updated_by")
    public int updatedBy;

    public Event(int event_id, String name, String description) {
        this.id = event_id;
        this.name = name;
        this.description = description;
    }

    public Event()
    {}
    
    public int getId() {
        return id;
    }

    public void setId(int event_id) {
        this.id = event_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }
}