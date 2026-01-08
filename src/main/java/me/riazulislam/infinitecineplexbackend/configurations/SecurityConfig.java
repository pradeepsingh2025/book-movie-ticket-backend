package me.riazulislam.infinitecineplexbackend.configurations;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.enums.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(c->c.disable())
                .authorizeHttpRequests(c -> c
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup", "/api/auth/login", "/api/auth/refresh").permitAll()
                        .requestMatchers("/api/health").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/movies/**", "/api/time-slots/**", "/api/show-times/**").permitAll()
                        .requestMatchers(request -> {
                            String path = request.getRequestURI();
                            String method = request.getMethod();
                            return (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) &&
                                    (path.startsWith("/api/movies/") || path.startsWith("/api/genres/") ||
                                            path.startsWith("/api/time-slots/") || path.startsWith("/api/day-time-slots/"));
                        }).hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole(RoleEnum.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(c ->
                        c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }
}
