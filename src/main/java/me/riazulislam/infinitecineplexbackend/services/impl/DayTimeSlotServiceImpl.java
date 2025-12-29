package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.models.DayTimeSlot;
import me.riazulislam.infinitecineplexbackend.models.TimeSlot;
import me.riazulislam.infinitecineplexbackend.repositories.DayTimeSlotRepository;
import me.riazulislam.infinitecineplexbackend.repositories.TimeSlotRepository;
import me.riazulislam.infinitecineplexbackend.services.DayTimeSlotService;
import me.riazulislam.infinitecineplexbackend.services.TimeSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class DayTimeSlotServiceImpl implements DayTimeSlotService {
    private final DayTimeSlotRepository dayTimeSlotRepository;
    @Override
    public DayTimeSlot createNewDayTimeSlot(DayTimeSlot dayTimeSlot) {
        try {
            return dayTimeSlotRepository.save(dayTimeSlot);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
