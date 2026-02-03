package com.cse.eventportal.repository;

import com.cse.eventportal.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatus(String status); // To show only active events
}