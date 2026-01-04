package me.riazulislam.infinitecineplexbackend.mappers;

import me.riazulislam.infinitecineplexbackend.dtos.CreateHallDTO;
import me.riazulislam.infinitecineplexbackend.dtos.HallDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateHallDTO;
import me.riazulislam.infinitecineplexbackend.models.Hall;
import org.springframework.stereotype.Component;

@Component
public class HallMapper {
    
    public HallDTO toDto(Hall hall) {
        if (hall == null) {
            return null;
        }
        return HallDTO.builder()
                .id(hall.getId())
                .availabilityStatus(hall.getAvailabilityStatus())
                .build();
    }
    
    public Hall toEntity(CreateHallDTO createHallDto) {
        if (createHallDto == null) {
            return null;
        }
        return Hall.builder()
                .availabilityStatus(createHallDto.getAvailabilityStatus())
                .build();
    }
    
    public void updateEntityFromDto(UpdateHallDTO updateHallDto, Hall hall) {
        if (updateHallDto == null || hall == null) {
            return;
        }
        if (updateHallDto.getAvailabilityStatus() != null) {
            hall.setAvailabilityStatus(updateHallDto.getAvailabilityStatus());
        }
    }
}