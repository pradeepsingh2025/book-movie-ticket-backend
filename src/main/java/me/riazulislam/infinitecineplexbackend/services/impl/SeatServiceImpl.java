package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.models.Seat;
import me.riazulislam.infinitecineplexbackend.repositories.SeatRepository;
import me.riazulislam.infinitecineplexbackend.services.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;

    @Override
    public Seat createNewSeat(Seat seat) {
        try {
            if (seatRepository.existsSeatsBySeat(seat.getSeat())){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Seat is already existed");
            }

            return seatRepository.save(seat);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
