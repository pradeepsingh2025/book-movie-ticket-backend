package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.LoginRequestDTO;
import me.riazulislam.infinitecineplexbackend.dtos.LoginResponseDTO;
import me.riazulislam.infinitecineplexbackend.dtos.CreateUserDTO;
import me.riazulislam.infinitecineplexbackend.models.Jwt;
import me.riazulislam.infinitecineplexbackend.models.User;
import me.riazulislam.infinitecineplexbackend.repositories.UserRepository;
import me.riazulislam.infinitecineplexbackend.services.AuthService;
import me.riazulislam.infinitecineplexbackend.services.UserService;
import me.riazulislam.infinitecineplexbackend.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        System.out.println("login page entered------------------------------------------");

        String email = loginRequestDTO.getEmail() == null ? null : loginRequestDTO.getEmail().trim().toLowerCase();

        System.out.println("Attempting authenticate for email: " + email);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            loginRequestDTO.getPassword()
                    )
            );
        } catch (Exception e) {
            System.err.println("Authentication failed for email: " + email + " - " + e.getClass().getSimpleName() + ": " + e.getMessage());
            throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials", e);
        }

        System.out.println("user authenticated------------------------------------------");
        User user = userRepository.findByEmail(email);
        System.out.println("user found--------------------------- " + (user != null ? user.getEmail() : "null"));
        Jwt accessToken = jwtUtils.generateAccessToken(user);
        Jwt refreshToken = jwtUtils.generateRefreshToken(user);
        System.out.println("accessToken and refreshToken generated-----------------------------");

        // Convert user to DTO
        me.riazulislam.infinitecineplexbackend.dtos.UserDTO userDTO = me.riazulislam.infinitecineplexbackend.dtos.UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole() == null ? null : user.getRole().name())
                .build();

        return new LoginResponseDTO(userDTO, accessToken, refreshToken);
    }

    @Override
    public LoginResponseDTO signup(CreateUserDTO createUserDTO) {
        // Normalize email and authenticate newly created user
        String email = createUserDTO.getEmail() == null ? null : createUserDTO.getEmail().trim().toLowerCase();
        System.out.println("Authenticating new signup for email: " + email);

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                email,
                createUserDTO.getPassword()
            )
        );

        // Load user
        User user = userRepository.findByEmail(email);

        // Generate tokens
        Jwt accessToken = jwtUtils.generateAccessToken(user);
        Jwt refreshToken = jwtUtils.generateRefreshToken(user);

        // Convert user to DTO
        me.riazulislam.infinitecineplexbackend.dtos.UserDTO userDTO = me.riazulislam.infinitecineplexbackend.dtos.UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole() == null ? null : user.getRole().name())
                .build();

        return new LoginResponseDTO(userDTO, accessToken, refreshToken);
    }

    @Override
    public Jwt refreshAccessToken(String refreshToken) {
        Jwt jwt = jwtUtils.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired()) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        User user = userRepository.findById(jwt.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return jwtUtils.generateAccessToken(user);
    }
}
