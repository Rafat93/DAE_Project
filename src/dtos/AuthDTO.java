package dtos;

import java.io.Serializable;

public class AuthDTO implements Serializable {
    private String email;
    private String password;

    public AuthDTO() {
    }

    public AuthDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
