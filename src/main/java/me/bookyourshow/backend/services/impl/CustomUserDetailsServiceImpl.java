package me.bookyourshow.backend.services.impl;

import lombok.AllArgsConstructor;
import me.bookyourshow.backend.models.User;
import me.bookyourshow.backend.repositories.UserRepository;
import me.bookyourshow.backend.services.CustomUserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found");

        String stored = user.getPassword();
        String masked = stored == null ? "null" : (stored.length() > 8 ? stored.substring(0, 6) + "...(" + stored.length() + ")" : stored);
        System.out.println("CustomUserDetailsService: loaded user for email='" + email + "' -> storedPassword=" + masked);

        var authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
