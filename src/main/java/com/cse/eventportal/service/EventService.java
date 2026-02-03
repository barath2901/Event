package com.cse.eventportal.service;

import com.cse.eventportal.model.Event;
import com.cse.eventportal.model.Participant;
import com.cse.eventportal.repository.EventRepository;
import com.cse.eventportal.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepo;
    private ParticipantRepository partRepo;

    // 1. Save or Create an Event
    public Event saveEvent(Event event) {
        // You can add logic here (e.g., set created date) if needed
        return eventRepo.save(event);
    }

    // 2. Get All Events (For Admin)
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    // 3. Get Only OPEN Events (For Students)
    public List<Event> getOpenEvents() {
        return eventRepo.findByStatus("OPEN");
    }

    // 4. Get a Single Event by ID
    public Event getEventById(Long id) {
        return eventRepo.findById(id).orElse(null);
    }
    // 1. Delete Event (and its students)
    public void deleteEvent(Long eventId) {
        Event event = eventRepo.findById(eventId).orElse(null);
        if(event != null) {
            // Delete all students registered for this event first
            List<Participant> students = partRepo.findByEvent(event);
            partRepo.deleteAll(students);

            // Now delete the event
            eventRepo.delete(event);
        }
    }
    // 2. Update Event
    public void updateEvent(Event updatedEvent) {
        eventRepo.save(updatedEvent); // .save() updates if ID exists
    }
}