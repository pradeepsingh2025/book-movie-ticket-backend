package me.riazulislam.infinitecineplexbackend.mappers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateShowTimeDTO;
import me.riazulislam.infinitecineplexbackend.dtos.MovieDTO;
import me.riazulislam.infinitecineplexbackend.dtos.ShowTimeDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateShowTimeDTO;
import me.riazulislam.infinitecineplexbackend.models.Hall;
import me.riazulislam.infinitecineplexbackend.models.Movie;
import me.riazulislam.infinitecineplexbackend.models.ShowTime;
import me.riazulislam.infinitecineplexbackend.models.Reservation;
import me.riazulislam.infinitecineplexbackend.repositories.HallRepository;
import me.riazulislam.infinitecineplexbackend.repositories.MovieRepository;
import me.riazulislam.infinitecineplexbackend.mappers.MovieMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ShowTimeMapper {
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final MovieMapper movieMapper;

    private me.riazulislam.infinitecineplexbackend.enums.DaysEnum dayFromDate(java.time.LocalDate date) {
        if (date == null) return null;
        java.time.DayOfWeek dow = date.getDayOfWeek();
        switch (dow) {
            case MONDAY: return me.riazulislam.infinitecineplexbackend.enums.DaysEnum.Monday;
            case TUESDAY: return me.riazulislam.infinitecineplexbackend.enums.DaysEnum.Tuesday;
            case WEDNESDAY: return me.riazulislam.infinitecineplexbackend.enums.DaysEnum.Wednesday;
            case THURSDAY: return me.riazulislam.infinitecineplexbackend.enums.DaysEnum.Thursday;
            case FRIDAY: return me.riazulislam.infinitecineplexbackend.enums.DaysEnum.Friday;
            case SATURDAY: return me.riazulislam.infinitecineplexbackend.enums.DaysEnum.Saturday;
            case SUNDAY: return me.riazulislam.infinitecineplexbackend.enums.DaysEnum.Sunday;
            default: return null;
        }
    }

    public ShowTime toModel(CreateShowTimeDTO showTimeDTO) {
        Movie movie = movieRepository.findById(showTimeDTO.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));

        Hall hall = null;
        if (showTimeDTO.getHallId() != null) {
            hall = hallRepository.findById(showTimeDTO.getHallId()).orElseThrow(() -> new RuntimeException("Hall not found"));
        }

        me.riazulislam.infinitecineplexbackend.enums.DaysEnum day = showTimeDTO.getDay() != null ? showTimeDTO.getDay() : dayFromDate(showTimeDTO.getShowDate());

        return ShowTime.builder()
                .showStatus(showTimeDTO.getStatus())
                .movie(movie)
                .hall(hall)
                .startTime(showTimeDTO.getStartTime())
                .endTime(showTimeDTO.getEndTime())
                .day(day)
                .showDate(showTimeDTO.getShowDate())
                .build();
    }

    public ShowTimeDTO toDTO(ShowTime showTime) {
        List<Long> reservationIds = showTime.getReservations() != null
                ? showTime.getReservations().stream().map(Reservation::getId).collect(Collectors.toList())
                : new ArrayList<>();

        Long hallId = showTime.getHall() != null ? showTime.getHall().getId() : null;

        return ShowTimeDTO.builder()
            .id(showTime.getId())
            .movie(movieMapper.toDTO(showTime.getMovie()))
            .status(String.valueOf(showTime.getShowStatus()))
            .day(showTime.getDay())
            .startTime(showTime.getStartTime())
            .endTime(showTime.getEndTime())
            .showDate(showTime.getShowDate())
            .hallId(hallId)
            .reservationIds(reservationIds)
            .build();
    }

    public ShowTime updateModel(ShowTime existing, UpdateShowTimeDTO dto) {
        if (dto.getStatus() != null) {
            existing.setShowStatus(dto.getStatus());
        }
        if (dto.getMovieId() != null) {
            Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
            existing.setMovie(movie);
        }
        if (dto.getHallId() != null) {
            Hall hall = hallRepository.findById(dto.getHallId()).orElseThrow(() -> new RuntimeException("Hall not found"));
            existing.setHall(hall);
        }
        if (dto.getDay() != null) {
            existing.setDay(dto.getDay());
        }
        if (dto.getStartTime() != null) {
            existing.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            existing.setEndTime(dto.getEndTime());
        }
        if (dto.getShowDate() != null) {
            existing.setShowDate(dto.getShowDate());
        }
        return existing;
    }
}
