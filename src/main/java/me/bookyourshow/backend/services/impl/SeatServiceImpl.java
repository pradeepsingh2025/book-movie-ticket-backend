package me.bookyourshow.backend.services.impl;

import me.bookyourshow.backend.dtos.CreateSeatDTO;
import me.bookyourshow.backend.dtos.SeatDTO;
import me.bookyourshow.backend.mappers.SeatMapper;
import me.bookyourshow.backend.models.Hall;
import me.bookyourshow.backend.models.Seat;
import me.bookyourshow.backend.models.ShowTime;
import me.bookyourshow.backend.repositories.HallRepository;
import me.bookyourshow.backend.repositories.SeatRepository;
import me.bookyourshow.backend.repositories.ShowTimeRepository;
import me.bookyourshow.backend.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private ShowTimeRepository showTimeRepository;

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private SeatMapper seatMapper;

	@Override
	public List<SeatDTO> getSeatsByShowTime(Long showTimeId) {
		ShowTime showTime = showTimeRepository.findById(showTimeId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ShowTime not found"));

		return seatRepository.findAll().stream()
				.filter(s -> s.getShowTime() != null && s.getShowTime().getId().equals(showTime.getId()))
				.map(seatMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public SeatDTO createSeat(Long showTimeId, CreateSeatDTO dto) {
		ShowTime showTime = showTimeRepository.findById(showTimeId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ShowTime not found"));

		Hall hall = showTime.getHall();
		if (hall == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ShowTime has no hall assigned");
		}

		Seat seat = Seat.builder()
				.seat(dto.getSeat())
				.status(dto.getStatus())
				.showTime(showTime)
				.hall(hall)
				.build();

		Seat saved = seatRepository.save(seat);
		return seatMapper.toDTO(saved);
	}

	@Override
	public SeatDTO getSeatById(Long id) {
		Seat seat = seatRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found"));
		return seatMapper.toDTO(seat);
	}

	@Override
	public SeatDTO updateSeat(Long id, CreateSeatDTO dto) {
		Seat seat = seatRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found"));

		if (dto.getSeat() != null) seat.setSeat(dto.getSeat());
		if (dto.getStatus() != null) seat.setStatus(dto.getStatus());

		Seat updated = seatRepository.save(seat);
		return seatMapper.toDTO(updated);
	}

	@Override
	public void deleteSeat(Long id) {
		Seat seat = seatRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found"));
		seatRepository.delete(seat);
	}
}
