package me.bookyourshow.backend.services;

import me.bookyourshow.backend.dtos.CreateSeatDTO;
import me.bookyourshow.backend.dtos.SeatDTO;

import java.util.List;

public interface SeatService {
	List<SeatDTO> getSeatsByShowTime(Long showTimeId);

	SeatDTO createSeat(Long showTimeId, CreateSeatDTO dto);

	SeatDTO getSeatById(Long id);

	SeatDTO updateSeat(Long id, CreateSeatDTO dto);

	void deleteSeat(Long id);
}
