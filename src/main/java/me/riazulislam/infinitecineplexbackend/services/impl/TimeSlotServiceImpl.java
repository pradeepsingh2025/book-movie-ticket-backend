package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.models.TimeSlot;
import me.riazulislam.infinitecineplexbackend.repositories.TimeSlotRepository;
import me.riazulislam.infinitecineplexbackend.services.TimeSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public TimeSlot createNewTimeSlot(TimeSlot timeSlot) {
        try {
            return timeSlotRepository.save(timeSlot);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
