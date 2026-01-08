package me.riazulislam.infinitecineplexbackend.configurations;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Getter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;


@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    private String secret;
    private int accessTokenExpiration;
    private int refreshTokenExpiration;
    private boolean secure;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
