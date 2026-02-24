package me.bookyourshow.backend.services;

import me.bookyourshow.backend.dtos.UpdateShowTimeDTO;
import me.bookyourshow.backend.enums.DaysEnum;
import me.bookyourshow.backend.models.ShowTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ShowTimeService {
    ShowTime createNewShowTime(ShowTime showTime);
    List<ShowTime> getAllShowTimes();
    ShowTime getShowTimeById(Long id);
    ShowTime updateShowTime(Long id, UpdateShowTimeDTO showTimeDTO);
    void deleteShowTime(Long id);
    List<ShowTime> getShowTimesByMovieId(Long movieId);

    List<ShowTime> getShowTimesByHallId(Long hallId);

    List<ShowTime> getShowTimesByShowDate(LocalDate showDate);

    List<ShowTime> getShowTimesByMovieIdAndShowDate(Long movieId, LocalDate showDate);

    List<ShowTime> getShowTimesByDay(DaysEnum day);

    /** Returns shows in the same hall on the date that overlap [startTime, endTime] */
    boolean isOverlappingShow(Long hallId, LocalDate showDate, LocalTime startTime, LocalTime endTime);
}
