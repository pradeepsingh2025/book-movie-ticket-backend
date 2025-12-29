package me.riazulislam.infinitecineplexbackend.mappers;

import me.riazulislam.infinitecineplexbackend.dtos.CreateSeatDTO;
import me.riazulislam.infinitecineplexbackend.dtos.SeatDTO;
import me.riazulislam.infinitecineplexbackend.models.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {
    public Seat toModel(CreateSeatDTO seatDTO) {
        return Seat.builder()
                .seat(seatDTO.getSeat())
                .status(seatDTO.getStatus())
                .build();
    }

    public SeatDTO toDTO(Seat seat) {
        return SeatDTO.builder()
                .seat(seat.getSeat())
                .status(seat.getStatus())
                .build();
    }
}
