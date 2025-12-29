package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.riazulislam.infinitecineplexbackend.models.Jwt;

@AllArgsConstructor
@Getter
public class LoginResponseDTO {
    private Jwt accessToken;
    private Jwt refreshToken;
}
