package me.riazulislam.infinitecineplexbackend.controllers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateSeatDTO;
import me.riazulislam.infinitecineplexbackend.mappers.SeatMapper;
import me.riazulislam.infinitecineplexbackend.models.Seat;
import me.riazulislam.infinitecineplexbackend.services.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seats")
@AllArgsConstructor
public class SeatController {
    private final SeatService seatService;
    private final SeatMapper seatMapper;

    @PostMapping
    public ResponseEntity<?> createNewSeat(@Validated @RequestBody CreateSeatDTO seatDTO) {
        Seat createdSeat = seatService.createNewSeat(seatMapper.toModel(seatDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(seatMapper.toDTO(createdSeat));
    }
}
