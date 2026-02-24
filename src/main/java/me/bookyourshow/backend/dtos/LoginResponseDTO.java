package me.bookyourshow.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bookyourshow.backend.models.Jwt;

@AllArgsConstructor
@Getter
public class LoginResponseDTO {
    private UserDTO user;
    private Jwt accessToken;
    private Jwt refreshToken;
}
