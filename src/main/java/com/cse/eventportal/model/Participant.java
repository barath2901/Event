package com.cse.eventportal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String regNo;
    private String dept;
    private String year;

    // CHANGED: Replaced 'lang' with 'phoneNumber'
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}