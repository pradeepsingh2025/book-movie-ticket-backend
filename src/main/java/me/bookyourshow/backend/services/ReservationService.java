package me.riazulislam.infinitecineplexbackend.services;

import me.riazulislam.infinitecineplexbackend.dtos.UpdateReservationDTO;
import me.riazulislam.infinitecineplexbackend.models.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation createNewReservation(Reservation reservation);
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    Reservation updateReservation(Long id, UpdateReservationDTO reservationDTO);
    void deleteReservation(Long id);
    List<Reservation> getReservationsByUserId(Long userId);
}
