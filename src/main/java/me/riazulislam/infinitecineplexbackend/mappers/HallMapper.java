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
                .name(hall.getName())
                .code(hall.getCode())
                .capacity(hall.getCapacity())
                .type(hall.getType())
                .availabilityStatus(hall.getAvailabilityStatus())
                .build();
    }
    
    public Hall toEntity(CreateHallDTO createHallDto) {
        if (createHallDto == null) {
            return null;
        }
        return Hall.builder()
                .name(createHallDto.getName())
                .code(createHallDto.getCode())
                .capacity(createHallDto.getCapacity())
                .type(createHallDto.getType())
                .availabilityStatus(createHallDto.getAvailabilityStatus())
                .build();
    }
    
    public void updateEntityFromDto(UpdateHallDTO updateHallDto, Hall hall) {
        if (updateHallDto == null || hall == null) {
            return;
        }
        if (updateHallDto.getName() != null) {
            hall.setName(updateHallDto.getName());
        }
        if (updateHallDto.getCode() != null) {
            hall.setCode(updateHallDto.getCode());
        }
        if (updateHallDto.getCapacity() != null) {
            hall.setCapacity(updateHallDto.getCapacity());
        }
        if (updateHallDto.getType() != null) {
            hall.setType(updateHallDto.getType());
        }
        if (updateHallDto.getAvailabilityStatus() != null) {
            hall.setAvailabilityStatus(updateHallDto.getAvailabilityStatus());
        }
    }
}