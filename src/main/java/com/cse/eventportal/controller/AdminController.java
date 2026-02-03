package com.cse.eventportal.controller;

import com.cse.eventportal.model.Event;
import com.cse.eventportal.model.Participant;
import com.cse.eventportal.service.EventService; // Import the Service
import com.cse.eventportal.repository.ParticipantRepository; // We still use PartRepo directly for now (to keep it simple)
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService; // CHANGED: Uses Service now

    @Autowired
    private ParticipantRepository partRepo;

    @GetMapping
    public String adminDashboard(Model model, HttpSession session) {
        // SECURITY CHECK
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        List<Event> allEvents = eventService.getAllEvents();

        // 1. Calculate Stats for the Dashboard
        long totalEvents = allEvents.size();
        long upcomingEvents = allEvents.stream()
                .filter(e -> "OPEN".equals(e.getStatus()))
                .count();
        // You can add a 'count' method to your ParticipantRepo later for real numbers
        long totalRegistrations = 0;

        // 2. Add to Model
        model.addAttribute("events", allEvents);
        model.addAttribute("totalEvents", totalEvents);
        model.addAttribute("upcomingEvents", upcomingEvents);
        model.addAttribute("totalRegistrations", totalRegistrations);

        model.addAttribute("newEvent", new Event());
        return "admin";
    }



    @PostMapping("/create-event")
    public String createEvent(@ModelAttribute Event event, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        event.setStatus("OPEN");
        eventService.saveEvent(event);
        return "redirect:/admin";
    }

    @GetMapping("/view/{id}")
    public String viewParticipants(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        Event event = eventService.getEventById(id); // CHANGED
        model.addAttribute("event", event);
        model.addAttribute("participants", partRepo.findByEvent(event));
        return "view-participants";
    }
    @GetMapping("/download/{id}")
    public void downloadCsv(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Event event = eventService.getEventById(id);
        List<Participant> participants = partRepo.findByEvent(event);

        // Set file name and content type
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"participants_" + event.getTitle() + ".csv\"");

        // Write Data
        PrintWriter writer = response.getWriter();
        writer.println("Register Number,Name,Department,Year,Language"); // CSV Header

        for (Participant p : participants) {
            writer.println(p.getRegNo() + "," + p.getName() + "," + p.getDept() + "," + p.getYear() + "," + p.getPhoneNumber());
        }
    }

    // --- FEATURE 2: DELETE EVENT ---
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        eventService.deleteEvent(id);
        return "redirect:/admin";
    }

    // --- FEATURE 3: EDIT EVENT (Show Form) ---
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "edit-event"; // We will create this HTML file next
    }

    // --- FEATURE 3: EDIT EVENT (Process Update) ---
    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute Event event, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        // Ensure the ID remains the same to update instead of create new
        event.setId(id);
        eventService.updateEvent(event);
        return "redirect:/admin";
    }

}