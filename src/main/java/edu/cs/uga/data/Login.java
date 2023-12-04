package edu.cs.uga.data;

/**
 * A java POJO class representing login credentials sent by a client.
 */
public class Login {
    private String email;
    private String password;

    public Login() {
        this.email = null;
        this.password = null;
    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
