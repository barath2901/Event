package com.cse.eventportal.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000) // Increased length for detailed descriptions
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    private String status; // OPEN or CLOSED

    // --- NEW FIELDS ---

    private String location; // e.g., "Computer Lab 3, Block B"

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime eventTime; // e.g., 14:00

    private Integer totalSpots; // e.g., 40

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime regDeadline; // e.g., 2026-02-18T23:59

    private String coordinators; // e.g., "Barath (III-CSE)"

    // Helper to check if deadline has passed
    public boolean isRegistrationExpired() {
        return regDeadline != null && LocalDateTime.now().isAfter(regDeadline);
    }
}