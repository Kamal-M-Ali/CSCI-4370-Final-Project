package edu.cs.uga.service;

import edu.cs.uga.data.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling endpoints.
 */
public class ApiService {
    public ResponseEntity<String> register(User user) {
        DatabaseService db = DatabaseService.getInstance();

        // ensure email does not already exist in the database
        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT * " +
                "FROM users " +
                "WHERE email=?")) {
            smnt.setString(1, user.getEmail());
            ResultSet rs = smnt.executeQuery();

            if (rs.next()) return ResponseEntity.badRequest().body("Account with that email already exists.");
        } catch (SQLException e) {
            return db.log(e);
        }

        PasswordService pw = new PasswordService();

        // create the user
        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "INSERT INTO users (email,pass,profile_name) " +
                "VALUES (?,?,?)")) {
            smnt.setString(1, user.getEmail());
            smnt.setString(2, pw.encryptPassword(user.getPassword()));
            smnt.setString(3, user.getProfile_name());
            smnt.execute();
        } catch (SQLException e) {
            return db.log(e);
        }

        // return the user id of the user just created
        try (PreparedStatement smnt = db.getConnection().prepareStatement("SELECT LAST_INSERT_ID()")) {
            ResultSet rs = smnt.executeQuery();

            if (rs.next())
                return ResponseEntity.ok(Integer.toString(rs.getInt(1)));
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to get new user_id.");
    }

    public ResponseEntity<String> login(Login login) {
        DatabaseService db = DatabaseService.getInstance();
        PasswordService pw = new PasswordService();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT user_id, pass " +
                "FROM users " +
                "WHERE email=?")) {
            smnt.setString(1, login.getEmail());
            ResultSet rs = smnt.executeQuery();

            if (rs.next()) {
                if (pw.matches(login.getPassword(), rs.getString("pass")))
                    return ResponseEntity.ok(Integer.toString(rs.getInt("user_id")));
                else
                    return ResponseEntity.status(403).body("Incorrect password.");
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.status(404).body("Account doesn't exist for that email.");
    }

    public ResponseEntity<?> profile(int userId) {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT * " +
                        "FROM users " +
                        "WHERE user_id=?")) {
            smnt.setInt(1, userId);
            ResultSet rs = smnt.executeQuery();

            if (rs.next()) {
                User user = new User(userId,
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getString("profile_name"));
                return ResponseEntity.ok(user);
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.status(404).body("Could not find user.");
    }

    public ResponseEntity<String> updateInfo(int userId, String email, String profileName) {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "UPDATE users " +
                        "SET email=?, profile_name=?" +
                        "WHERE user_id=?")) {
            smnt.setString(1, email);
            smnt.setString(2, profileName);
            smnt.setInt(3, userId);
            smnt.execute();
            if (smnt.getUpdateCount() == 1)
                return ResponseEntity.ok("Successfully updated profile.");
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.status(404).body("Could not find user.");
    }

    public ResponseEntity<String> changePassword(int userId, String password) {
        DatabaseService db = DatabaseService.getInstance();
        PasswordService pw = new PasswordService();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "UPDATE users " +
                        "SET pass=?" +
                        "WHERE user_id=?")) {
            smnt.setString(1, pw.encryptPassword(password));
            smnt.setInt(2, userId);
            smnt.execute();
            if (smnt.getUpdateCount() == 1)
                return ResponseEntity.ok("Successfully changed password.");
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.status(404).body("Could not find user.");
    }

    public ResponseEntity<String> addGame(int userId, Game game) {
        DatabaseService db = DatabaseService.getInstance();

        // add to game table
        if (checkUser(userId) && addMedia(game)) {
            // add to game table
            try (PreparedStatement smnt = db.getConnection().prepareStatement(
                    "INSERT INTO game (game_id,release_date,developers,platforms,plays) " +
                            "VALUES((SELECT LAST_INSERT_ID()), ?, ?, ?, ?)")) {
                smnt.setDate(1, game.getRelease_date());
                smnt.setString(2, game.getDevelopers());
                smnt.setString(3, game.getPlatforms());
                smnt.setInt(4, game.getPlays());
                smnt.execute();
                if (smnt.getUpdateCount() == 1)
                    return ResponseEntity.ok("Successfully added game.");
            } catch (SQLException e) {
                return db.log(e);
            }
        }

        return ResponseEntity.internalServerError().body("Failed to add game.");
    }

    public ResponseEntity<String> addMovie(int userId, Movie movie) {
        DatabaseService db = DatabaseService.getInstance();

        if (checkUser(userId) && addMedia(movie)) {
            // add to movie table
            try (PreparedStatement smnt = db.getConnection().prepareStatement(
                    "INSERT INTO movie (movie_id,homepage,budget,production,runtime,release_date) " +
                            "VALUES((SELECT LAST_INSERT_ID()), ?, ?, ?, ?, ?)")) {
                smnt.setString(1, movie.getHomepage());
                smnt.setLong(2, movie.getBudget());
                smnt.setString(3, movie.getProduction());
                smnt.setInt(4, movie.getRuntime());
                smnt.setDate(5, movie.getRelease_date());
                smnt.execute();
                if (smnt.getUpdateCount() == 1)
                    return ResponseEntity.ok("Successfully added movie.");
            } catch (SQLException e) {
                return db.log(e);
            }
        }

        return ResponseEntity.internalServerError().body("Failed to add movie.");
    }

    public ResponseEntity<String> addShow(int userId, TvShow tvShow) {
        DatabaseService db = DatabaseService.getInstance();

        // add to game table
        if (checkUser(userId) && addMedia(tvShow)) {
            // add to game table
            try (PreparedStatement smnt = db.getConnection().prepareStatement(
                    "INSERT INTO game (tv_show_id,num_seasons,num_episodes,is_adult,first_air_date,last_air_date,homepage,created_by,networks) " +
                            "VALUES((SELECT LAST_INSERT_ID()), ?, ?, ?, ?, ?, ?, ?, ?)")) {
                smnt.setInt(1, tvShow.getNum_seasons());
                smnt.setInt(2, tvShow.getNum_episodes());
                smnt.setBoolean(3, tvShow.isIs_adult());
                smnt.setDate(4, tvShow.getFirst_air_date());
                smnt.setDate(5, tvShow.getLast_air_date());
                smnt.setString(6, tvShow.getHomepage());
                smnt.setString(7, tvShow.getCreated_by());
                smnt.setString(8, tvShow.getNetworks());
                smnt.execute();
                if (smnt.getUpdateCount() == 1)
                    return ResponseEntity.ok("Successfully added tv show.");
            } catch (SQLException e) {
                return db.log(e);
            }
        }

        return ResponseEntity.internalServerError().body("Failed to add tv show.");
    }

    public ResponseEntity<String> addBook(int userId, Book book) {
        DatabaseService db = DatabaseService.getInstance();

        // add to game table
        if (checkUser(userId) && addMedia(book)) {
            // add to game table
            try (PreparedStatement smnt = db.getConnection().prepareStatement(
                    "INSERT INTO game (book_id,author,series,num_pages,publication_date,publishers) " +
                            "VALUES((SELECT LAST_INSERT_ID()), ?, ?, ?, ?, ?)")) {
                smnt.setString(1, book.getAuthor());
                smnt.setString(2, book.getSeries());
                smnt.setInt(3, book.getNum_pages());
                smnt.setDate(4, book.getPublication_date());
                smnt.setString(5, book.getPublishers());
                smnt.execute();
                if (smnt.getUpdateCount() == 1)
                    return ResponseEntity.ok("Successfully added book.");
            } catch (SQLException e) {
                return db.log(e);
            }
        }

        return ResponseEntity.internalServerError().body("Failed to add book.");
    }

    private boolean checkUser(int userId) {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT user_id " +
                        "FROM users " +
                        "WHERE user_id=?")) {
            smnt.setInt(1, userId);
            ResultSet rs = smnt.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException e) {
            db.log(e);
        }
        return false;
    }

    private boolean addMedia(Media media) {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "INSERT INTO media (title,score,summary,genres,review_count) " +
                        "VALUES (?, ?, ?, ?, ?)")) {
            smnt.setString(1, media.getTitle());
            if (media.getScore() == null)
                smnt.setNull(2, java.sql.Types.DOUBLE);
            else
                smnt.setDouble(2, media.getScore());
            smnt.setString(3, media.getSummary());
            smnt.setString(4, media.getGenres());
            smnt.setInt(5, media.getReview_count());
            smnt.execute();
            return true;
        } catch (SQLException e) {
            db.log(e);
        }
        return false;
    }

    public ResponseEntity<?> getAllMedia(String mediaType) {
        DatabaseService db = DatabaseService.getInstance();
        List<Media> media = new ArrayList<>();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT media_id,title,score,summary,genres,review_count " +
                "FROM media JOIN " + mediaType + " ON media_id=" + mediaType + "_id")) {
            ResultSet rs = smnt.executeQuery();

            while (rs.next()) {
                Media mediaObject = new Media(rs.getString("title"),
                        rs.getDouble("score"),
                        rs.getString("summary"),
                        rs.getString("genres"),
                        rs.getInt("review_count"));
                mediaObject.setMedia_id(rs.getInt("media_id"));
                media.add(mediaObject);
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.ok(media);
    }
}
