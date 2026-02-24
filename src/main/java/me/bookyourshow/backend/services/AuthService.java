package me.bookyourshow.backend.services;

import me.bookyourshow.backend.dtos.LoginRequestDTO;
import me.bookyourshow.backend.dtos.LoginResponseDTO;
import me.bookyourshow.backend.dtos.CreateUserDTO;
import me.bookyourshow.backend.models.AuthResult;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    LoginResponseDTO signup(CreateUserDTO createUserDTO);

    AuthResult refreshAccessToken(String refreshToken);
}
