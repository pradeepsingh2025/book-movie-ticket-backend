package me.riazulislam.infinitecineplexbackend.controllers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateReservationDTO;
import me.riazulislam.infinitecineplexbackend.dtos.ReservationDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateReservationDTO;
import me.riazulislam.infinitecineplexbackend.mappers.ReservationMapper;
import me.riazulislam.infinitecineplexbackend.models.Reservation;
import me.riazulislam.infinitecineplexbackend.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody CreateReservationDTO reservationDTO) {
        Reservation createdReservation = reservationService.createNewReservation(reservationMapper.toModel(reservationDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationMapper.toDTO(createdReservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        List<ReservationDTO> reservationDTOs = reservations.stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTOs);
    }

    @GetMapping("/my-reservations")
    public ResponseEntity<List<ReservationDTO>> getMyReservations(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        List<ReservationDTO> reservationDTOs = reservations.stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservationMapper.toDTO(reservation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id, @RequestBody UpdateReservationDTO reservationDTO) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservationDTO);
        return ResponseEntity.ok(reservationMapper.toDTO(updatedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
