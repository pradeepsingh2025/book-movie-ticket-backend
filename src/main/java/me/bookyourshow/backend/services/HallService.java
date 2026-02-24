package me.bookyourshow.backend.services;

import me.bookyourshow.backend.dtos.CreateHallDTO;
import me.bookyourshow.backend.dtos.HallDTO;
import me.bookyourshow.backend.dtos.UpdateHallDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface HallService {
    HallDTO createHall(CreateHallDTO createHallDto);
    HallDTO getHallById(Long id);
    List<HallDTO> getAllHalls();
    List<HallDTO> getAvailableHalls(LocalDate date, LocalTime startTime, LocalTime endTime);
    HallDTO updateHall(Long id, UpdateHallDTO updateHallDto);
    void deleteHall(Long id);
}