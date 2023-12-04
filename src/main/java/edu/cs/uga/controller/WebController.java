package edu.cs.uga.controller;

import edu.cs.uga.data.*;
import edu.cs.uga.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/api/admin/add-game/:{userId}")
    public ResponseEntity<String> addBook(@PathVariable int userId, @RequestBody Game game) {
        return apiService.addGame(userId, game);
    }

    @PutMapping("/api/admin/add-movie/:{userId}")
    public ResponseEntity<String> addMovie(@PathVariable int userId, @RequestBody Movie movie) {
        return apiService.addMovie(userId, movie);
    }

    @PutMapping("/api/admin/add-show/:{userId}")
    public ResponseEntity<String> addShow(@PathVariable int userId, @RequestBody TvShow tvShow) {
        return apiService.addShow(userId, tvShow);
    }

    @PutMapping("/api/admin/add-book/:{userId}")
    public ResponseEntity<String> addBook(@PathVariable int userId, @RequestBody Book book) {
        return apiService.addBook(userId, book);
    }

    @GetMapping("/api/media/:{mediaType}")
    public ResponseEntity<?> getAllGames(@PathVariable String mediaType) {
        return apiService.getAllMedia(mediaType);
    }
}
