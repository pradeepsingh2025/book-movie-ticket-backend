package me.bookyourshow.backend.mappers;

import lombok.AllArgsConstructor;
import me.bookyourshow.backend.dtos.CreateReservationDTO;
import me.bookyourshow.backend.dtos.ReservationDTO;
import me.bookyourshow.backend.dtos.SeatDTO;
import me.bookyourshow.backend.dtos.UpdateReservationDTO;
import me.bookyourshow.backend.models.Reservation;
import me.bookyourshow.backend.models.Seat;
import me.bookyourshow.backend.models.ShowTime;
import me.bookyourshow.backend.models.User;
import me.bookyourshow.backend.repositories.ShowTimeRepository;
import me.bookyourshow.backend.repositories.SeatRepository;
import me.bookyourshow.backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ReservationMapper {
    private final UserRepository userRepository;
    private final ShowTimeRepository showTimeRepository;
    private final SeatRepository seatRepository;
    private final ShowTimeMapper showTimeMapper;
    private final UserMapper userMapper;
    private final SeatMapper seatMapper;

    public Reservation toModel(CreateReservationDTO reservationDTO) {
        List<Long> seatIds = reservationDTO.getReservationSeatsId();
        List<Seat> seats = new ArrayList<>();

        Long userId = reservationDTO.getUserId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id cannot be null");
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with " + userId + " not found"));

        Long showTimeId = reservationDTO.getShowTimeId();
        if (showTimeId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Show time id cannot be null");
        }
        ShowTime showTime = showTimeRepository.findById(showTimeId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Show time with " + showTimeId + " not found"));

        Reservation reservation = Reservation.builder()
                .user(user)
                .showTime(showTime)
                .build();

        List<me.bookyourshow.backend.models.ReservationSeat> reservationSeats = new ArrayList<>();
        for (Long id : seatIds) {
            if (id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seat id cannot be null");
            }
            Seat seat = seatRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat with " + id + " not found"));
            reservationSeats.add(me.bookyourshow.backend.models.ReservationSeat.builder()
                    .reservation(reservation)
                    .seat(seat)
                    .build());
        }
        reservation.setReservationSeats(reservationSeats);

        return reservation;
    }

    public ReservationDTO toDTO(Reservation reservation) {
        List<SeatDTO> reservationSeats = new ArrayList<>();

        for (me.bookyourshow.backend.models.ReservationSeat rs : reservation.getReservationSeats()) {
            reservationSeats.add(seatMapper.toDTO(rs.getSeat()));
        }

        return ReservationDTO.builder()
                .user(userMapper.toDTO(reservation.getUser()))
                .show_time(showTimeMapper.toDTO(reservation.getShowTime()))
                .reservation_seats(reservationSeats)
                .build();
    }

    public Reservation updateModel(Reservation existing, UpdateReservationDTO dto) {
        if (dto.getUser_id() != null) {
            User user = userRepository.findById(dto.getUser_id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User with " + dto.getUser_id() + " not found"));
            existing.setUser(user);
        }
        if (dto.getShow_time_id() != null) {
            ShowTime showTime = showTimeRepository.findById(dto.getShow_time_id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Show time with " + dto.getShow_time_id() + " not found"));
            existing.setShowTime(showTime);
        }
        if (dto.getReservation_seats_id() != null) {
            List<me.bookyourshow.backend.models.ReservationSeat> newReservationSeats = new ArrayList<>();
            for (Long id : dto.getReservation_seats_id()) {
                Seat seat = seatRepository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat with " + id + " not found"));
                newReservationSeats.add(me.bookyourshow.backend.models.ReservationSeat.builder()
                        .reservation(existing)
                        .seat(seat)
                        .build());
            }
            existing.getReservationSeats().clear();
            existing.getReservationSeats().addAll(newReservationSeats);
        }
        // reservation seats removed — no-op
        return existing;
    }
}
