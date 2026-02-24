package me.bookyourshow.backend.models;

public class AuthResult {

    private final String accessToken;
    private final User user;

    public AuthResult(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public User getUser() {
        return user;
    }
}
