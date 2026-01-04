package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HallDTO {
    private Long id;
    private Map<Long, String> availabilityStatus = new HashMap<>();
}