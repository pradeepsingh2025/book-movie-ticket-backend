package me.bookyourshow.backend.mappers;

import lombok.AllArgsConstructor;
import me.bookyourshow.backend.enums.DaysEnum;
import me.bookyourshow.backend.dtos.CreateShowTimeDTO;
import me.bookyourshow.backend.dtos.ShowTimeDTO;
import me.bookyourshow.backend.dtos.UpdateShowTimeDTO;
import me.bookyourshow.backend.models.Hall;
import me.bookyourshow.backend.models.Movie;
import me.bookyourshow.backend.models.ShowTime;
import me.bookyourshow.backend.models.Reservation;
import me.bookyourshow.backend.repositories.HallRepository;
import me.bookyourshow.backend.repositories.MovieRepository;
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
    private final HallMapper hallMapper;

    private DaysEnum dayFromDate(java.time.LocalDate date) {
        if (date == null) return null;
        java.time.DayOfWeek dow = date.getDayOfWeek();
        switch (dow) {
            case MONDAY: return DaysEnum.Monday;
            case TUESDAY: return DaysEnum.Tuesday;
            case WEDNESDAY: return DaysEnum.Wednesday;
            case THURSDAY: return DaysEnum.Thursday;
            case FRIDAY: return DaysEnum.Friday;
            case SATURDAY: return DaysEnum.Saturday;
            case SUNDAY: return DaysEnum.Sunday;
            default: return null;
        }
    }

    public ShowTime toModel(CreateShowTimeDTO showTimeDTO) {
        Movie movie = movieRepository.findById(showTimeDTO.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));

        Hall hall = null;
        if (showTimeDTO.getHallId() != null) {
            hall = hallRepository.findById(showTimeDTO.getHallId()).orElseThrow(() -> new RuntimeException("Hall not found"));
        }

        DaysEnum day = showTimeDTO.getDay() != null ? showTimeDTO.getDay() : dayFromDate(showTimeDTO.getShowDate());

        return ShowTime.builder()
                .showStatus(showTimeDTO.getStatus())
                .movie(movie)
                .hall(hall)
                .startTime(showTimeDTO.getStartTime())
                .endTime(showTimeDTO.getEndTime())
            .price(showTimeDTO.getPrice())
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
            .hall(hallMapper.toDto(showTime.getHall()))
            .reservationIds(reservationIds)
                .price(showTime.getPrice())
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
        if (dto.getPrice() != null) {
            existing.setPrice(dto.getPrice());
        }
        return existing;
    }
}
