package me.riazulislam.infinitecineplexbackend.mappers;

import me.riazulislam.infinitecineplexbackend.dtos.CreateTimeSlotDTO;
import me.riazulislam.infinitecineplexbackend.dtos.TimeSlotDTO;
import me.riazulislam.infinitecineplexbackend.models.TimeSlot;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {

    public TimeSlotDTO toDTO(TimeSlot timeSlot) {
        if (timeSlot == null) {
            return null;
        }
        return TimeSlotDTO.builder()
                .id(timeSlot.getId())
                .name(timeSlot.getName())
                .startTime(timeSlot.getStartTime())
                .duration(timeSlot.getDuration())
                .build();
    }

    public TimeSlot toEntity(CreateTimeSlotDTO dto) {
        if (dto == null) {
            return null;
        }
        return TimeSlot.builder()
                .name(dto.getName())
                .startTime(dto.getStart_time())
                .duration(dto.getDuration())
                .build();
    }
}
