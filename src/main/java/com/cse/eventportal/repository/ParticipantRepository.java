package com.cse.eventportal.repository;

import com.cse.eventportal.model.Participant;
import com.cse.eventportal.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByEvent(Event event); // Fetch students for a specific event
}