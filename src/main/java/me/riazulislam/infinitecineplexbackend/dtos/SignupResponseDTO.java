package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponseDTO {
    private UserDTO user;
    private String accessToken;
}