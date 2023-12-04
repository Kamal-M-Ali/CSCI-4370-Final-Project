package edu.cs.uga.service;

import edu.cs.uga.data.Login;
import edu.cs.uga.data.User;
import org.springframework.http.ResponseEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            if (smnt.getUpdateCount() <= 0)
                return ResponseEntity.badRequest().body("Failed to insert into users.");
        } catch (SQLException e) {
            return db.log(e);
        }

        // return the user id of the user just created
        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT user_id " +
                "FROM users " +
                "WHERE email=?")) {
            smnt.setString(1, user.getEmail());
            ResultSet rs = smnt.executeQuery();

            if (rs.next())
                return ResponseEntity.ok(Integer.toString(rs.getInt("user_id")));
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
}
