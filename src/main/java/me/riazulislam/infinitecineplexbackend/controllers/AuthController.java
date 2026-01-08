package me.riazulislam.infinitecineplexbackend.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.configurations.JwtConfig;
import me.riazulislam.infinitecineplexbackend.dtos.CreateUserDTO;
import me.riazulislam.infinitecineplexbackend.dtos.LoginRequestDTO;
import me.riazulislam.infinitecineplexbackend.dtos.LoginResponseDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UserDTO;
import me.riazulislam.infinitecineplexbackend.dtos.SignupResponseDTO;
import me.riazulislam.infinitecineplexbackend.models.Jwt;
import me.riazulislam.infinitecineplexbackend.models.JwtResponse;
import me.riazulislam.infinitecineplexbackend.services.AuthService;
import me.riazulislam.infinitecineplexbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MissingRequestCookieException;

import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtConfig jwtConfig;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signUp(@Valid @RequestBody CreateUserDTO user, HttpServletResponse response) {


        //It returns created user DTO from createNewUser
        UserDTO createdUser = userService.createNewUser(user);


        LoginResponseDTO loginResponse = authService.signup(user);


        // Store refresh token in HttpOnly cookie
        addRefreshTokenCookie(
                response,
                loginResponse.getRefreshToken().toString()
        );


        SignupResponseDTO responseBody = new SignupResponseDTO(
                loginResponse.getUser(),
                loginResponse.getAccessToken().toString()
        );


        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<SignupResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        LoginResponseDTO loginResponse = authService.login(loginRequestDTO);

        addRefreshTokenCookie(
                response,
                loginResponse.getRefreshToken().toString()
        );
        System.out.println("returning from login service in AuthController-------------------------------------------");
        
        SignupResponseDTO responseBody = new SignupResponseDTO(
                loginResponse.getUser(),
                loginResponse.getAccessToken().toString()
        );
        
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@CookieValue(value = "refreshToken") String refreshToken) {
        Jwt accessToken = authService.refreshAccessToken(refreshToken);
        return new JwtResponse(accessToken.toString());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    // ================================
    // HELPERS
    // ================================
    private void addRefreshTokenCookie(
            HttpServletResponse response,
            String refreshToken
    ) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(jwtConfig.isSecure());
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        response.addCookie(cookie);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<Void> handleMissingCookie() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
