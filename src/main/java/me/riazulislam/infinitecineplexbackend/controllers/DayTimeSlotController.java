package me.riazulislam.infinitecineplexbackend.controllers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateDayTimeSlotDTO;
import me.riazulislam.infinitecineplexbackend.dtos.CreateTimeSlotDTO;
import me.riazulislam.infinitecineplexbackend.mappers.DayTimeSlotMapper;
import me.riazulislam.infinitecineplexbackend.mappers.TimeSlotMapper;
import me.riazulislam.infinitecineplexbackend.models.DayTimeSlot;
import me.riazulislam.infinitecineplexbackend.models.TimeSlot;
import me.riazulislam.infinitecineplexbackend.services.DayTimeSlotService;
import me.riazulislam.infinitecineplexbackend.services.TimeSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/day-time-slots")
public class DayTimeSlotController {
    private final DayTimeSlotService dayTimeSlotService;
    private final DayTimeSlotMapper dayTimeSlotMapper;

    @PostMapping
    public ResponseEntity<?> createDayTimeSlot(@Validated @RequestBody CreateDayTimeSlotDTO dayTimeSlotDTO) {
        DayTimeSlot createdDayTimeSlot = dayTimeSlotService.createNewDayTimeSlot(dayTimeSlotMapper.toEntity(dayTimeSlotDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDayTimeSlot);
    }
}
