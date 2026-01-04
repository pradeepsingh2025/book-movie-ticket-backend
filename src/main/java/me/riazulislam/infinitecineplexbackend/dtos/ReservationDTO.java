package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private ShowTimeDTO show_time;
    private UserDTO user;
    
}
