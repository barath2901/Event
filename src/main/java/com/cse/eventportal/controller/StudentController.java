package com.cse.eventportal.controller;

import com.cse.eventportal.model.Event;
import com.cse.eventportal.model.Participant;
import com.cse.eventportal.service.EventService; // Import Service
import com.cse.eventportal.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private EventService eventService; // CHANGED: Uses Service

    @Autowired
    private ParticipantRepository partRepo;

    @GetMapping("/")
    public String home(Model model) {
        // Only show OPEN events to students
        model.addAttribute("events", eventService.getOpenEvents()); // CHANGED
        return "index";
    }

    @GetMapping("/register/{eventId}")
    public String showRegisterForm(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEventById(eventId); // CHANGED
        model.addAttribute("event", event);
        model.addAttribute("participant", new Participant());
        return "register";
    }

    @PostMapping("/register/{eventId}")
    public String registerStudent(@PathVariable Long eventId, @ModelAttribute Participant participant) {
        Event event = eventService.getEventById(eventId); // CHANGED
        participant.setEvent(event);
        partRepo.save(participant);
        return "redirect:/?success";
    }
}