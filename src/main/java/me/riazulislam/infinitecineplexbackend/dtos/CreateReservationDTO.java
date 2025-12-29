package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDTO {
    private Long show_time_id;
    private Long user_id;
    private List<Long> reservation_seats_id;
}
