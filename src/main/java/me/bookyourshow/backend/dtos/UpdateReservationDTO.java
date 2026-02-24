package me.bookyourshow.backend.dtos;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReservationDTO {
    private Long show_time_id;
    private Long user_id;
    private List<Long> reservation_seats_id;
}
