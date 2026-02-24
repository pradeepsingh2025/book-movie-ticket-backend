package me.bookyourshow.backend.mappers;

import me.bookyourshow.backend.dtos.UserDTO;
import me.bookyourshow.backend.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().toString())
                .build();
    }
}
