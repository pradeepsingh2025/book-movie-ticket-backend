package me.riazulislam.infinitecineplexbackend.controllers;

import me.riazulislam.infinitecineplexbackend.dtos.CreateSeatDTO;
import me.riazulislam.infinitecineplexbackend.dtos.SeatDTO;
import me.riazulislam.infinitecineplexbackend.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SeatController {

	@Autowired
	private SeatService seatService;

	@GetMapping("/showtimes/{id}/seats")
	public ResponseEntity<List<SeatDTO>> listByShowTime(@PathVariable("id") Long id) {
		return ResponseEntity.ok(seatService.getSeatsByShowTime(id));
	}

	@PostMapping("/showtimes/{showTimeId}/seats")
	public ResponseEntity<SeatDTO> createForShowTime(@PathVariable Long showTimeId, @RequestBody CreateSeatDTO dto) {
		SeatDTO created = seatService.createSeat(showTimeId, dto);
		return ResponseEntity.status(201).body(created);
	}

	@GetMapping("/seats/{id}")
	public ResponseEntity<SeatDTO> getOne(@PathVariable Long id) {
		return ResponseEntity.ok(seatService.getSeatById(id));
	}

	@PutMapping("/seats/{id}")
	public ResponseEntity<SeatDTO> update(@PathVariable Long id, @RequestBody CreateSeatDTO dto) {
		return ResponseEntity.ok(seatService.updateSeat(id, dto));
	}

	@DeleteMapping("/seats/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		seatService.deleteSeat(id);
		return ResponseEntity.noContent().build();
	}
}

