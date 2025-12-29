package me.riazulislam.infinitecineplexbackend.mappers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateDayTimeSlotDTO;
import me.riazulislam.infinitecineplexbackend.dtos.DayTimeSlotDTO;
import me.riazulislam.infinitecineplexbackend.models.DayTimeSlot;
import me.riazulislam.infinitecineplexbackend.models.TimeSlot;
import me.riazulislam.infinitecineplexbackend.repositories.TimeSlotRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DayTimeSlotMapper {
    private final TimeSlotMapper timeSlotMapper;
    private final TimeSlotRepository timeSlotRepository;

    public DayTimeSlotDTO toDTO(DayTimeSlot dayTimeSlot) {
        if (dayTimeSlot == null) {
            return null;
        }
        return DayTimeSlotDTO.builder()
                .id(dayTimeSlot.getId())
                .day(dayTimeSlot.getDay())
                .timeSlot(timeSlotMapper.toDTO(dayTimeSlot.getTimeSlot()))
                .build();
    }

    public DayTimeSlot toEntity(CreateDayTimeSlotDTO dto) {
        if (dto == null) {
            return null;
        }
        TimeSlot timeSlot = timeSlotRepository.findById(dto.getTime_slot()).orElseThrow(() -> new RuntimeException("Time slot not found"));
        return DayTimeSlot.builder()
                .day(dto.getDay())
                .timeSlot(timeSlot)
                .build();
    }
}
