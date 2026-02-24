package me.riazulislam.infinitecineplexbackend.dtos;

import jakarta.validation.constraints.Min;
import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.HallType;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateHallDTO {
    private String name;
    private String code;
    
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
    
    private HallType type;
    private Map<Long, String> availabilityStatus = new HashMap<>();
}