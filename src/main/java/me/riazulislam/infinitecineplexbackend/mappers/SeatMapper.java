package me.riazulislam.infinitecineplexbackend.mappers;

import me.riazulislam.infinitecineplexbackend.dtos.SeatDTO;
import me.riazulislam.infinitecineplexbackend.models.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {
	public SeatDTO toDTO(Seat seat) {
		if (seat == null) return null;
		return SeatDTO.builder()
				.id(seat.getId())
				.seat(seat.getSeat())
				.status(seat.getStatus())
				.showTimeId(seat.getShowTime() != null ? seat.getShowTime().getId() : null)
				.hallId(seat.getHall() != null ? seat.getHall().getId() : null)
				.build();
	}
}
