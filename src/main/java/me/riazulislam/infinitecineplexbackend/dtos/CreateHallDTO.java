package me.riazulislam.infinitecineplexbackend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateHallDTO {
    @NotNull(message = "Availability status cannot be null")
    private Map<Long, String> availabilityStatus = new HashMap<>();
}