package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDTO {

    @JsonAlias({ "show_time_id", "showTimeId", "showtimeId" })
    private Long showTimeId;

    @JsonAlias({ "user_id", "userId" })
    private Long userId;

    @NotEmpty(message = "reservationSeatsId cannot be empty")
    @JsonAlias({ "reservation_seats_id", "reservationSeatsId", "reservation_seat_ids", "seatreservation_seats_id" })
    private List<Long> reservationSeatsId = new ArrayList<>();
}
