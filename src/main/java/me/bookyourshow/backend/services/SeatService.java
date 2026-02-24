package me.riazulislam.infinitecineplexbackend.services;

import me.riazulislam.infinitecineplexbackend.dtos.CreateSeatDTO;
import me.riazulislam.infinitecineplexbackend.dtos.SeatDTO;

import java.util.List;

public interface SeatService {
	List<SeatDTO> getSeatsByShowTime(Long showTimeId);

	SeatDTO createSeat(Long showTimeId, CreateSeatDTO dto);

	SeatDTO getSeatById(Long id);

	SeatDTO updateSeat(Long id, CreateSeatDTO dto);

	void deleteSeat(Long id);
}
