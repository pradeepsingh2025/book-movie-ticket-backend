package me.bookyourshow.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.bookyourshow.backend.dtos.UserDTO;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private UserDTO user;

    public String getToken() {
        return token;
    }

    public UserDTO getUser() {
        return user;
    }
}
