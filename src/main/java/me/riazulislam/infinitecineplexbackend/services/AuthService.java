package me.riazulislam.infinitecineplexbackend.services;

import me.riazulislam.infinitecineplexbackend.dtos.LoginRequestDTO;
import me.riazulislam.infinitecineplexbackend.dtos.LoginResponseDTO;
import me.riazulislam.infinitecineplexbackend.dtos.CreateUserDTO;
import me.riazulislam.infinitecineplexbackend.models.Jwt;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    LoginResponseDTO signup(CreateUserDTO createUserDTO);

    Jwt refreshAccessToken(String refreshToken);
}
