package me.riazulislam.infinitecineplexbackend.services;

import me.riazulislam.infinitecineplexbackend.dtos.CreateHallDTO;
import me.riazulislam.infinitecineplexbackend.dtos.HallDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateHallDTO;

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