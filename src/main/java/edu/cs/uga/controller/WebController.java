package edu.cs.uga.controller;

import edu.cs.uga.data.Login;
import edu.cs.uga.service.ApiService;
import edu.cs.uga.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for mapping endpoints to service code.
 */
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
@Controller
public class WebController {
    private final ApiService apiService;
    WebController() { apiService = new ApiService(); }

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return apiService.register(user);
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody Login login) {
        return apiService.login(login);
    }

    @GetMapping("/api/profile/:{userId}")
    public ResponseEntity<?> profile(@PathVariable int userId) {
        return apiService.profile(userId);
    }

    @PostMapping("/api/update-info/:{userId}")
    public ResponseEntity<String> updateInfo(@PathVariable int userId, @RequestParam String email, @RequestParam String profileName) {
        return apiService.updateInfo(userId, email, profileName);
    }

    @PostMapping("/api/change-password/:{userId}")
    public ResponseEntity<String> changePassword(@PathVariable int userId, @RequestParam String password) {
        return apiService.changePassword(userId, password);
    }

}
