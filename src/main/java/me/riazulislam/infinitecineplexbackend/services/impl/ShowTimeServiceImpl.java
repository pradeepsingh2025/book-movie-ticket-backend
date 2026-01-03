package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateShowTimeDTO;
import me.riazulislam.infinitecineplexbackend.mappers.ShowTimeMapper;
import me.riazulislam.infinitecineplexbackend.models.ShowTime;
import me.riazulislam.infinitecineplexbackend.repositories.ShowTimeRepository;
import me.riazulislam.infinitecineplexbackend.services.ShowTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ShowTimeServiceImpl implements ShowTimeService {
    private final ShowTimeRepository showTimeRepository;
    private final ShowTimeMapper showTimeMapper;

    @Override
    public ShowTime createNewShowTime(ShowTime showTime) {
        try {
            if (showTimeRepository.existsShowTimeByMovieAndDayTimeSlotAndShowDate(showTime.getMovie(), showTime.getDayTimeSlot(), showTime.getShowDate())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Movie with this slot is already exist");
            }

            return showTimeRepository.save(showTime);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
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
}
