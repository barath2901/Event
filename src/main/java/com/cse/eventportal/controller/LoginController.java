package com.cse.eventportal.controller;

import com.cse.eventportal.model.Admin;
import com.cse.eventportal.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private AdminRepository adminRepo;

    // 1. Show Login Page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // 2. Process Login
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Admin admin = adminRepo.findByUsername(username);

        if (admin != null && admin.getPassword().equals(password)) {
            // SUCCESS: Save user in session
            session.setAttribute("loggedInUser", admin.getUsername());
            return "redirect:/admin";
        } else {
            // FAILURE
            model.addAttribute("error", "Invalid Username or Password");
            return "login";
        }
    }

    // 3. Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Kill session
        return "redirect:/login";
    }
}