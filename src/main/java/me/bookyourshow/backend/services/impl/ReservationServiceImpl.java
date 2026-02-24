package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateReservationDTO;
import me.riazulislam.infinitecineplexbackend.enums.SeatStatusEnum;
import me.riazulislam.infinitecineplexbackend.mappers.ReservationMapper;
import me.riazulislam.infinitecineplexbackend.models.Reservation;
import me.riazulislam.infinitecineplexbackend.models.Seat;
import me.riazulislam.infinitecineplexbackend.repositories.ReservationRepository;
import me.riazulislam.infinitecineplexbackend.repositories.SeatRepository;
import me.riazulislam.infinitecineplexbackend.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public Reservation createNewReservation(Reservation reservation) {
        try {
            List<Seat> managedSeats = new ArrayList<>();

            for (Seat detachedSeat : reservation.getSeats()) {
                // Fetch the managed entity to ensure we update the live record
                Seat seat = seatRepository.findById(detachedSeat.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found"));

                if (seat.getStatus() != SeatStatusEnum.ACTIVE) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                            "Seat " + seat.getSeat() + " of this show can not be booked");
                }

                boolean alreadyBooked = reservationRepository
                        .existsByShowTime_IdAndSeats_Id(reservation.getShowTime().getId(), seat.getId());

                if (alreadyBooked) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Seat " + seat.getSeat() + " of this show is already booked");
                }

                seat.setStatus(SeatStatusEnum.INACTIVE);
                managedSeats.add(seat);
            }

            // Save the updated seats and attach them to the reservation
            seatRepository.saveAll(managedSeats);
            reservation.setSeats(managedSeats);

            return reservationRepository.save(reservation);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        try {
            return reservationRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Reservation getReservationById(Long id) {
        try {
            return reservationRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Reservation updateReservation(Long id, UpdateReservationDTO reservationDTO) {
        try {
            Reservation existingReservation = getReservationById(id);
            reservationMapper.updateModel(existingReservation, reservationDTO);
            return reservationRepository.save(existingReservation);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteReservation(Long id) {
        try {
            if (!reservationRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
            }
            reservationRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long userId) {
        try {
            return reservationRepository.findByUser_Id(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
