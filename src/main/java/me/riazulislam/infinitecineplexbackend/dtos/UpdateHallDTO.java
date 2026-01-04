package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateHallDTO {
    private Map<Long, String> availabilityStatus = new HashMap<>();
}