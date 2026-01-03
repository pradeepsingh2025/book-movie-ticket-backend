package me.riazulislam.infinitecineplexbackend.services;

import me.riazulislam.infinitecineplexbackend.dtos.UpdateShowTimeDTO;
import me.riazulislam.infinitecineplexbackend.models.ShowTime;

import java.util.List;

public interface ShowTimeService {
    ShowTime createNewShowTime(ShowTime showTime);
    List<ShowTime> getAllShowTimes();
    ShowTime getShowTimeById(Long id);
    ShowTime updateShowTime(Long id, UpdateShowTimeDTO showTimeDTO);
    void deleteShowTime(Long id);
    List<ShowTime> getShowTimesByMovieId(Long movieId);
}
