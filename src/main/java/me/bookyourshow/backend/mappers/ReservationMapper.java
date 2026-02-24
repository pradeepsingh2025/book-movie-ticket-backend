package me.riazulislam.infinitecineplexbackend.mappers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateReservationDTO;
import me.riazulislam.infinitecineplexbackend.dtos.ReservationDTO;
import me.riazulislam.infinitecineplexbackend.dtos.SeatDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateReservationDTO;
import me.riazulislam.infinitecineplexbackend.models.Reservation;
import me.riazulislam.infinitecineplexbackend.models.Seat;
import me.riazulislam.infinitecineplexbackend.models.ShowTime;
import me.riazulislam.infinitecineplexbackend.models.User;
import me.riazulislam.infinitecineplexbackend.repositories.ShowTimeRepository;
import me.riazulislam.infinitecineplexbackend.repositories.SeatRepository;
import me.riazulislam.infinitecineplexbackend.repositories.UserRepository;
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
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with " + userId + " not found"));

        Long showTimeId = reservationDTO.getShowTimeId();
        if (showTimeId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Show time id cannot be null");
        }
        ShowTime showTime = showTimeRepository.findById(showTimeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Show time with " + showTimeId + " not found"));

        for(Long id: seatIds) {
            if (id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seat id cannot be null");
            }
            seats.add(seatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat with " + id + " not found")));
        }

        return Reservation.builder()
                .user(user)
                .showTime(showTime)
                .seats(seats)
                .build();
    }

    public ReservationDTO toDTO(Reservation reservation) {
        List<SeatDTO> reservationSeats = new ArrayList<>();

        for(Seat seat: reservation.getSeats()){
            reservationSeats.add(seatMapper.toDTO(seat));
        }

        return ReservationDTO.builder()
                .user(userMapper.toDTO(reservation.getUser()))
                .show_time(showTimeMapper.toDTO(reservation.getShowTime()))
                .reservation_seats(reservationSeats)
                .build();
    }

    public Reservation updateModel(Reservation existing, UpdateReservationDTO dto) {
        if (dto.getUser_id() != null) {
            User user = userRepository.findById(dto.getUser_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with " + dto.getUser_id() + " not found"));
            existing.setUser(user);
        }
        if (dto.getShow_time_id() != null) {
            ShowTime showTime = showTimeRepository.findById(dto.getShow_time_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Show time with " + dto.getShow_time_id() + " not found"));
            existing.setShowTime(showTime);
        }
        if (dto.getReservation_seats_id() != null) {
            List<Seat> seats = new ArrayList<>();
            for (Long id : dto.getReservation_seats_id()) {
                seats.add(seatRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat with " + id + " not found")));
            }
            existing.setSeats(seats);
        }
        // reservation seats removed — no-op
        return existing;
    }
}
