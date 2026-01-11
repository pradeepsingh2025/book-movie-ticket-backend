package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateShowTimeDTO;
import me.riazulislam.infinitecineplexbackend.mappers.ShowTimeMapper;
import me.riazulislam.infinitecineplexbackend.models.Hall;
import me.riazulislam.infinitecineplexbackend.models.Seat;
import me.riazulislam.infinitecineplexbackend.models.ShowTime;
import me.riazulislam.infinitecineplexbackend.repositories.ShowTimeRepository;

import me.riazulislam.infinitecineplexbackend.repositories.SeatRepository;
import me.riazulislam.infinitecineplexbackend.services.ShowTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import me.riazulislam.infinitecineplexbackend.enums.DaysEnum;
import me.riazulislam.infinitecineplexbackend.enums.SeatStatusEnum;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShowTimeServiceImpl implements ShowTimeService {
    private final ShowTimeRepository showTimeRepository;
    private final SeatRepository seatRepository;
    private final ShowTimeMapper showTimeMapper;

    @Override
    @Transactional
    public ShowTime createNewShowTime(ShowTime showTime) {

        try {

            // Prevent overlapping shows in the same hall on the same date
            if (showTime.getHall() != null) {
                Long hallId = showTime.getHall().getId();
                List<ShowTime> overlapping = showTimeRepository
                        .findByHallIdAndShowDateAndStartTimeLessThanAndEndTimeGreaterThan(
                                hallId, showTime.getShowDate(), showTime.getEndTime(), showTime.getStartTime());
                if (!overlapping.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Show time overlaps with existing show in the hall");
                }
            }
            // 2️⃣ Save show first (required to get show_id)
            ShowTime savedShowTime = showTimeRepository.save(showTime);

            // 3️⃣ Create seats based on hall capacity
            Hall hall = savedShowTime.getHall();

            int capacity = hall.getCapacity();
            int seatsPerRow = 10;

            List<Seat> seats = new ArrayList<>(capacity);

            int seatCounter = 0;
            int rowIndex = 0;

            while (seatCounter < capacity) {

                String row = rowLabel(rowIndex);

                for (int seatNumber = 1; seatNumber <= seatsPerRow && seatCounter < capacity; seatNumber++) {

                    Seat seat = Seat.builder()
                            .seat(row + seatNumber) // e.g. A1, B7
                            .showTime(savedShowTime)
                            .hall(hall)
                            .status(SeatStatusEnum.ACTIVE)
                            .build();

                    seats.add(seat);
                    seatCounter++;
                }

                rowIndex++;
            }

            seatRepository.saveAll(seats);

            return savedShowTime;

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    // 👇 DEFINE IT HERE (PRIVATE HELPER)
    private String rowLabel(int rowIndex) {
        StringBuilder label = new StringBuilder();
        rowIndex++; // make it 1-based

        while (rowIndex > 0) {
            rowIndex--;
            label.insert(0, (char) ('A' + (rowIndex % 26)));
            rowIndex /= 26;
        }

        return label.toString();
    }

    @Override
    public List<ShowTime> getAllShowTimes() {
        try {
            return showTimeRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public ShowTime getShowTimeById(Long id) {
        try {
            return showTimeRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ShowTime not found"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public ShowTime updateShowTime(Long id, UpdateShowTimeDTO showTimeDTO) {
        try {
            ShowTime existingShowTime = getShowTimeById(id);
            showTimeMapper.updateModel(existingShowTime, showTimeDTO);
            return showTimeRepository.save(existingShowTime);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteShowTime(Long id) {
        try {
            if (!showTimeRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ShowTime not found");
            }
            showTimeRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<ShowTime> getShowTimesByMovieId(Long movieId) {
        try {
            return showTimeRepository.findByMovieId(movieId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<ShowTime> getShowTimesByHallId(Long hallId) {
        try {
            return showTimeRepository.findByHallId(hallId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<ShowTime> getShowTimesByShowDate(LocalDate showDate) {
        try {
            return showTimeRepository.findByShowDate(showDate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<ShowTime> getShowTimesByMovieIdAndShowDate(Long movieId, LocalDate showDate) {
        try {
            return showTimeRepository.findByMovieIdAndShowDate(movieId, showDate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<ShowTime> getShowTimesByDay(DaysEnum day) {
        try {
            return showTimeRepository.findByDay(day);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public boolean isOverlappingShow(Long hallId, LocalDate showDate, LocalTime startTime, LocalTime endTime) {
        try {
            List<ShowTime> overlapping = showTimeRepository
                    .findByHallIdAndShowDateAndStartTimeLessThanAndEndTimeGreaterThan(hallId, showDate, endTime,
                            startTime);
            return !overlapping.isEmpty();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
