package edu.cs.uga.service;

import edu.cs.uga.data.*;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service class for handling endpoints.
 */
public class ApiService {
    public ResponseEntity<String> total() {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT COUNT(media_id) AS ? " +
                "FROM media")) {
            smnt.setString(1, "media_total");
            ResultSet rs = smnt.executeQuery();

            if (rs.next()) {
                return ResponseEntity.ok(Integer.toString(rs.getInt("media_total")));
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to count media.");
    }

    public ResponseEntity<?> register(User user) {
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

            if (rs.next()) {
                String[] res = {Integer.toString(rs.getInt(1)), user.getProfile_name()};
                return ResponseEntity.ok(res);
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to get new user_id.");
    }

    public ResponseEntity<?> login(Login login) {
        DatabaseService db = DatabaseService.getInstance();
        PasswordService pw = new PasswordService();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT user_id, pass, profile_name " +
                "FROM users " +
                "WHERE email=?")) {
            smnt.setString(1, login.getEmail());
            ResultSet rs = smnt.executeQuery();

            if (rs.next()) {
                if (pw.matches(login.getPassword(), rs.getString("pass"))) {
                    String[] res = {Integer.toString(rs.getInt(1)), rs.getString("profile_name")};
                    return ResponseEntity.ok(res);
                } else {
                    return ResponseEntity.status(403).body("Incorrect password.");
                }
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
                "SET email=?, profile_name=? " +
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
                "SET pass=? " +
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
                    "INSERT INTO tv_show (tv_show_id,num_seasons,num_episodes,is_adult,first_air_date,last_air_date,homepage,created_by,networks) " +
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
                    "INSERT INTO book (book_id,author,series,num_pages,publication_date,publishers) " +
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
            if (media.getScore() < 0)
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

    public ResponseEntity<?> getAllMedia(String mediaType, String sort, String searchTerm) {
        DatabaseService db = DatabaseService.getInstance();
        List<Media> media = new ArrayList<>();

        String order = "title ASC";
        switch (sort) {
            case "ratingh" -> order = "score DESC";
            case "ratingl" -> order = "score ASC";
        }

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT media_id,title,score,summary,genres,review_count " +
                "FROM media JOIN " + mediaType + " ON media_id=" + mediaType + "_id " +
                "WHERE title LIKE ? " +
                "ORDER BY " + order)) {
            smnt.setString(1, "%" + searchTerm + "%");

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

    public ResponseEntity<?> getMedia(String mediaType, int mediaId) {
        DatabaseService db = DatabaseService.getInstance();
        List<Object> list = new ArrayList<>();
        Media object = new Media();

        switch (mediaType) {
            case "game" -> object = new Game();
            case "movie" -> object = new Movie();
            case "tv_show" -> object = new TvShow();
            case "book" -> object = new Book();
        }

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT * " +
                        "FROM media JOIN " + mediaType + " ON media_id=" + mediaType + "_id " +
                        "WHERE media_id=?")) {
            smnt.setInt(1, mediaId);
            ResultSet rs = smnt.executeQuery();

            if (rs.next()) {
                object.setMedia_id(rs.getInt("media_id"));
                object.setTitle(rs.getString("title"));
                object.setScore(rs.getDouble("score"));
                object.setSummary(rs.getString("summary"));
                object.setGenres(rs.getString("genres"));
                object.setReview_count(rs.getInt("review_count"));

                switch (mediaType) {
                    case "game" -> {
                        Game game = (Game) object;
                        game.setRelease_date(rs.getDate("release_date"));
                        game.setDevelopers(rs.getString("developers"));
                        game.setPlatforms(rs.getString("platforms"));
                        game.setPlays(rs.getInt("plays"));
                        list.add(game);
                    }
                    case "movie" -> {
                        Movie movie = (Movie) object;
                        movie.setHomepage(rs.getString("homepage"));
                        movie.setBudget(rs.getLong("budget"));
                        movie.setProduction(rs.getString("production"));
                        movie.setRuntime(rs.getInt("runtime"));
                        movie.setRelease_date(rs.getDate("release_date"));
                        list.add(movie);
                    }
                    case "tv_show" -> {
                        TvShow show = (TvShow) object;
                        show.setNum_seasons(rs.getInt("num_seasons"));
                        show.setNum_episodes(rs.getInt("num_episodes"));
                        show.setIs_adult(rs.getBoolean("is_adult"));
                        show.setFirst_air_date(rs.getDate("first_air_date"));
                        show.setLast_air_date(rs.getDate("last_air_date"));
                        show.setHomepage(rs.getString("homepage"));
                        show.setCreated_by(rs.getString("created_by"));
                        show.setNetworks(rs.getString("networks"));
                        list.add(show);
                    }
                    case "book" -> {
                        Book book = (Book) object;
                        book.setAuthor(rs.getString("author"));
                        book.setSeries(rs.getString("series"));
                        book.setNum_pages(rs.getInt("num_pages"));
                        book.setPublication_date(rs.getDate("publication_date"));
                        book.setPublishers(rs.getString("publishers"));
                        list.add(book);
                    }
                }

                // get reviews
                try (PreparedStatement getReviews = db.getConnection().prepareStatement(
                        "SELECT review_id,body,score,media_id,user_id,created,profile_name " +
                        "FROM review NATURAL JOIN media_review NATURAL JOIN users " +
                        "WHERE media_id=?")) {
                    getReviews.setInt(1, mediaId);
                    ResultSet rs2 = getReviews.executeQuery();
                    List<Review> reviews = new ArrayList<>();

                    while (rs2.next()) {
                        Review review = new Review(
                                rs2.getString("body"),
                                rs2.getDouble("score"),
                                rs2.getInt("media_id"),
                                rs2.getInt("user_id"),
                                rs2.getDate("created"),
                                rs2.getString("profile_name")
                        );
                        review.setReview_id(rs2.getInt("review_id"));
                        reviews.add(review);
                    }

                    list.add(reviews);
                }

                // get comments
                try (PreparedStatement getComments = db.getConnection().prepareStatement(
                        "SELECT comment_id,body,media_id,user_id,created,profile_name " +
                        "FROM comments NATURAL JOIN media_comment NATURAL JOIN users " +
                        "WHERE media_id=?")) {
                    getComments.setInt(1, mediaId);
                    ResultSet rs3 = getComments.executeQuery();
                    List<Comment> comments = new ArrayList<>();

                    while (rs3.next()) {
                        Comment comment = new Comment(
                                rs3.getString("body"),
                                rs3.getInt("media_id"),
                                rs3.getInt("user_id"),
                                rs3.getDate("created"),
                                rs3.getString("profile_name")
                        );
                        comment.setComment_id(rs3.getInt("comment_id"));
                        comments.add(comment);
                    }

                    list.add(comments);
                }

                //System.out.println(list);
                return ResponseEntity.ok(list);
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to query database.");
    }

    public ResponseEntity<?> rate(int mediaId, double score) {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT score, review_count " +
                "FROM media " +
                "WHERE media_id=?")) {
            smnt.setInt(1, mediaId);
            ResultSet rs = smnt.executeQuery();

            if (rs.next()) {
                double oldScore = rs.getDouble("score");
                int oldReviewCount = rs.getInt("review_count");

                try (PreparedStatement upd = db.getConnection().prepareStatement(
                        "UPDATE media " +
                        "SET review_count=?, score=? " +
                        "WHERE media_id=?")) {
                    upd.setInt(1, ++oldReviewCount);
                    double newScore = ((oldReviewCount - 1) * oldScore + score) / oldReviewCount;
                    upd.setDouble(2, newScore);
                    upd.setInt(3, mediaId);
                    upd.execute();

                    if (upd.getUpdateCount() == 1) {
                        return ResponseEntity.ok(newScore);
                    }
                }

            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to add review");
    }

    public ResponseEntity<String> addComment(Comment comment) {
        DatabaseService db = DatabaseService.getInstance();
        comment.setCreated(new Date());

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "INSERT INTO comments (body) " +
                        "VALUES (?)")) {
            smnt.setString(1, comment.getBody());
            smnt.execute();

            if (smnt.getUpdateCount() == 1) {
                try (PreparedStatement smnt2 = db.getConnection().prepareStatement(
                        "INSERT INTO media_comment (media_id,user_id,comment_id,created) " +
                                "VALUES(?, ?, (SELECT LAST_INSERT_ID()), ?)")) {
                    smnt2.setInt(1, comment.getMedia_id());
                    smnt2.setInt(2, comment.getUser_id());
                    smnt2.setTimestamp(3, new Timestamp(comment.getCreated().getTime()));
                    smnt2.execute();

                    if (smnt2.getUpdateCount() == 1)
                        return ResponseEntity.ok("Added comment successfully");
                }
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to add comment");
    }

    public ResponseEntity<?> getProfile(int userId) {
        DatabaseService db = DatabaseService.getInstance();
        List<Object> list = new ArrayList<>();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT profile_name " +
                "FROM users " +
                "WHERE user_id=?")) {
            smnt.setInt(1, userId);
            ResultSet rs = smnt.executeQuery();

            if (rs.next()) {
                list.add(rs.getString("profile_name"));
                List<Comment> comments = new ArrayList<>();

                // get media comments
                try (PreparedStatement getComments = db.getConnection().prepareStatement(
                        "SELECT title, created, body " +
                                "FROM comments NATURAL JOIN media_comment NATURAL JOIN media " +
                                "WHERE user_id=?")) {
                    getComments.setInt(1, userId);
                    ResultSet rs2 = getComments.executeQuery();

                    while (rs2.next()) {
                        Comment comment = new Comment();
                        comment.setExtra(rs2.getString("title"));
                        comment.setCreated(rs2.getDate("created"));
                        comment.setBody(rs2.getString("body"));
                        comments.add(comment);
                    }
                }

                // get thread comments
                try (PreparedStatement getComments = db.getConnection().prepareStatement(
                        "SELECT title, created, comments.body " +
                                "FROM comments NATURAL JOIN thread_comment NATURAL JOIN thread " +
                                "WHERE user_id=?")) {
                    getComments.setInt(1, userId);
                    ResultSet rs2 = getComments.executeQuery();

                    while (rs2.next()) {
                        Comment comment = new Comment();
                        comment.setExtra(rs2.getString("title"));
                        comment.setCreated(rs2.getDate("created"));
                        comment.setBody(rs2.getString("comments.body"));
                        comments.add(comment);
                    }
                }

                // add comments to response
                list.add(comments);

                // get posts
                try (PreparedStatement getThreads = db.getConnection().prepareStatement(
                        "SELECT title,category,created " +
                                "FROM thread NATURAL JOIN forum " +
                                "WHERE user_id=?")) {
                    getThreads.setInt(1, userId);
                    ResultSet rs2 = getThreads.executeQuery();
                    List<Post> posts = new ArrayList<>();

                    while (rs2.next()) {
                        Post post = new Post();
                        post.setTitle(rs2.getString("title"));
                        post.setCategory(rs2.getString("category"));
                        post.setCreated(rs2.getDate("created"));
                        posts.add(post);
                    }

                    list.add(posts);
                }

                return ResponseEntity.ok(list);
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to query database.");
    }

    public ResponseEntity<?> getCategory(String category) {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT * " +
                "FROM forum NATURAL JOIN thread NATURAL JOIN users " +
                "WHERE category=?")) {
            smnt.setString(1, category);
            ResultSet rs = smnt.executeQuery();

            List<Post> posts = new ArrayList<>();

            while (rs.next()) {
                Post post = new Post(
                        rs.getString("title"),
                        rs.getString("body"),
                        rs.getInt("user_id"),
                        rs.getDate("created"),
                        rs.getInt("forum_id")
                );
                post.setThread_id(rs.getInt("thread_id"));
                post.setCategory(rs.getString("category"));
                post.setProfile_name(rs.getString("profile_name"));
                posts.add(post);
            }
            return ResponseEntity.ok(posts);
        } catch (SQLException e) {
            return db.log(e);
        }
    }

    public ResponseEntity<String> createPost(String category, Post post) {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "INSERT INTO thread (title,body,user_id,created,forum_id) " +
                "VALUES (?,?,?,?,(SELECT forum_id FROM forum WHERE category=?))")) {
            smnt.setString(1, post.getTitle());
            smnt.setString(2, post.getBody());
            smnt.setInt(3, post.getUser_id());
            post.setCreated(new Date());
            smnt.setTimestamp(4, new Timestamp(post.getCreated().getTime()));
            smnt.setString(5, category);
            smnt.execute();

            if (smnt.getUpdateCount() == 1) {
                return ResponseEntity.ok("Successfully created post.");
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to execute query.");
    }

    public ResponseEntity<?> getPostComments(int threadId) {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "SELECT comment_id,body,thread_id,user_id,created,profile_name " +
                "FROM comments NATURAL JOIN thread_comment NATURAL JOIN users " +
                "WHERE thread_id=?")) {
            smnt.setInt(1, threadId);
            ResultSet rs = smnt.executeQuery();
            List<ThreadComment> comments = new ArrayList<>();

            while (rs.next()) {
                ThreadComment comment = new ThreadComment(
                        rs.getString("body"),
                        rs.getInt("thread_id"),
                        rs.getInt("user_id"),
                        rs.getDate("created"),
                        rs.getString("profile_name")
                );
                comment.setComment_id(rs.getInt("comment_id"));
                comments.add(comment);
            }
            return ResponseEntity.ok(comments);
        } catch (SQLException e) {
            return db.log(e);
        }
    }

    public ResponseEntity<String> addCommentToThread(ThreadComment comment) {
        DatabaseService db = DatabaseService.getInstance();
        comment.setCreated(new Date());

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "INSERT INTO comments (body) " +
                "VALUES (?)")) {
            smnt.setString(1, comment.getBody());
            smnt.execute();

            if (smnt.getUpdateCount() == 1) {
                try (PreparedStatement smnt2 = db.getConnection().prepareStatement(
                        "INSERT INTO thread_comment (thread_id,user_id,comment_id,created) " +
                        "VALUES(?, ?, (SELECT LAST_INSERT_ID()), ?)")) {
                    smnt2.setInt(1, comment.getThread_id());
                    smnt2.setInt(2, comment.getUser_id());
                    smnt2.setTimestamp(3, new Timestamp(comment.getCreated().getTime()));
                    smnt2.execute();

                    if (smnt2.getUpdateCount() == 1)
                        return ResponseEntity.ok("Added comment successfully");
                }
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to add comment");
    }

    public ResponseEntity<String> deletePost(int threadId, int userId) {
        DatabaseService db = DatabaseService.getInstance();

        try (PreparedStatement smnt = db.getConnection().prepareStatement(
                "DELETE FROM thread " +
                "WHERE thread_id=?")) {
            smnt.setInt(1, threadId);
            smnt.execute();

            if (smnt.getUpdateCount() == 1) {
                return ResponseEntity.ok("Deleted post.");
            }
        } catch (SQLException e) {
            return db.log(e);
        }

        return ResponseEntity.internalServerError().body("Failed to delete post.");
    }
}
