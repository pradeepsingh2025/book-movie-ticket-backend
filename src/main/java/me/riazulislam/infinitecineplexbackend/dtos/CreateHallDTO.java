package me.riazulislam.infinitecineplexbackend.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.HallType;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateHallDTO {
    @NotBlank(message = "Hall name is required")
    private String name;
    
    @NotBlank(message = "Hall code is required")
    private String code;
    
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
    
    @NotNull(message = "Hall type is required")
    private HallType type;
    
    private Map<Long, String> availabilityStatus = new HashMap<>();
}