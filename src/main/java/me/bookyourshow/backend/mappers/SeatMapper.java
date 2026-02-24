package me.bookyourshow.backend.mappers;

import me.bookyourshow.backend.dtos.CreateSeatDTO;
import me.bookyourshow.backend.dtos.SeatDTO;
import me.bookyourshow.backend.dtos.UpdateSeatDTO;
import me.bookyourshow.backend.models.Seat;
import me.bookyourshow.backend.models.ShowTime;
import me.bookyourshow.backend.models.Hall;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

	public Seat toModel(CreateSeatDTO seatDTO) {
		Seat.SeatBuilder builder = Seat.builder()
				.seat(seatDTO.getSeat())
				.status(seatDTO.getStatus());

		if (seatDTO.getShowTimeId() != null) {
			ShowTime showTime = new ShowTime();
			showTime.setId(seatDTO.getShowTimeId());
			builder.showTime(showTime);
		}

		if (seatDTO.getHallId() != null) {
			Hall hall = new Hall();
			hall.setId(seatDTO.getHallId());
			builder.hall(hall);
		}

		return builder.build();
	}

	public SeatDTO toDTO(Seat seat) {
		if (seat == null)
			return null;
		return SeatDTO.builder()
				.id(seat.getId())
				.seat(seat.getSeat())
				.status(seat.getStatus())
				.showTimeId(seat.getShowTime() != null ? seat.getShowTime().getId() : null)
				.hallId(seat.getHall() != null ? seat.getHall().getId() : null)
				.build();
	}

	public void updateFromDto(Seat seat, UpdateSeatDTO dto) {
		if (seat == null || dto == null)
			return;
		if (dto.getStatus() != null)
			seat.setStatus(dto.getStatus());
	}
}
