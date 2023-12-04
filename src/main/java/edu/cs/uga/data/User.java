package edu.cs.uga.data;

/**
 * A Java POJO class representing a user entity.
 */
public class User {
    private int id;
    private String email;
    private String password;
    private String profile_name;

    public User() {
        this.id = -1;
        this.email = null;
        this.password = null;
        this.profile_name = null;
    }

    public User(String email, String password, String profile_name) { this(-1, email, password, profile_name); }
    public User(int id, String email, String password, String profile_name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profile_name = profile_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profile_name='" + profile_name + '\'' +
                '}';
    }
}
