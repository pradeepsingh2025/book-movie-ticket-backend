package me.riazulislam.infinitecineplexbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.configurations.JwtConfig;
import me.riazulislam.infinitecineplexbackend.models.Jwt;
import me.riazulislam.infinitecineplexbackend.models.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@AllArgsConstructor
@Component
public class JwtUtils {
    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(User user, long tokenExpiration) {
        System.out.println("start generating tokens----------------------- " + tokenExpiration);
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();
        System.out.println("END generating tokens---------------------------------- " + tokenExpiration);
        System.out.println("claims---------------------------------------" + claims);
        System.out.println("secret key----------------------------------- " + jwtConfig.getSecretKey());

        Jwt token = new Jwt(claims, jwtConfig.getSecretKey());

        System.out.println("printing token before returning--------------------------- " + token);
        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt parseToken(String token) {
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
