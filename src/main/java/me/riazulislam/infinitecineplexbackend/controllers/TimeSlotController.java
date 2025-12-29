package me.riazulislam.infinitecineplexbackend.controllers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateTimeSlotDTO;
import me.riazulislam.infinitecineplexbackend.mappers.TimeSlotMapper;
import me.riazulislam.infinitecineplexbackend.models.TimeSlot;
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
@RequestMapping("/api/time-slots")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;
    private final TimeSlotMapper timeSlotMapper;
    @PostMapping
    public ResponseEntity<?> createTimeSlot(@Validated @RequestBody CreateTimeSlotDTO timeSlotDTO) {
        TimeSlot createdTimeSlot = timeSlotService.createNewTimeSlot(timeSlotMapper.toEntity(timeSlotDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTimeSlot);
    }
}
