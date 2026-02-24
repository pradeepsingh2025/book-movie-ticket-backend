package me.riazulislam.infinitecineplexbackend.services;

import me.riazulislam.infinitecineplexbackend.dtos.LoginRequestDTO;
import me.riazulislam.infinitecineplexbackend.dtos.LoginResponseDTO;
import me.riazulislam.infinitecineplexbackend.dtos.CreateUserDTO;
import me.riazulislam.infinitecineplexbackend.models.AuthResult;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    LoginResponseDTO signup(CreateUserDTO createUserDTO);

    AuthResult refreshAccessToken(String refreshToken);
}
