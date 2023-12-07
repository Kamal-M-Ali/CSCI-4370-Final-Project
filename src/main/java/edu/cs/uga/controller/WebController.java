package edu.cs.uga.controller;

import edu.cs.uga.data.*;
import edu.cs.uga.service.ApiService;
import org.apache.coyote.Response;
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

    @GetMapping("/api/total")
    public ResponseEntity<String> total() {
        return apiService.total();
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return apiService.register(user);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
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
    public ResponseEntity<?> getAllMedia(@PathVariable String mediaType, @RequestParam String sort, @RequestParam String searchTerm) {
        return apiService.getAllMedia(mediaType, sort, searchTerm);
    }

    @GetMapping("/api/media/:{mediaType}/:{mediaId}")
    public ResponseEntity<?> getMedia(@PathVariable String mediaType, @PathVariable int mediaId) {
        return apiService.getMedia(mediaType, mediaId);
    }

    @PutMapping("/api/rate-media/:{mediaId}")
    public ResponseEntity<?> rate(@PathVariable int mediaId, @RequestParam double score) {
        if (score > 5)
            return ResponseEntity.badRequest().body("Score cannot be more than 5.");

        return apiService.rate(mediaId, score);
    }

    @PutMapping("/api/add-comment")
    public ResponseEntity<String> addComment(@RequestBody Comment comment) {
        if (comment.getUser_id() < 0 || comment.getMedia_id() < 0)
            return ResponseEntity.badRequest().body("Missing user id and/or media id.");

        return apiService.addComment(comment);
    }
}
