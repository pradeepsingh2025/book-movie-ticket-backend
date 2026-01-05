package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.HallType;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HallDTO {
    private Long id;
    private String name;
    private String code;
    private Integer capacity;
    private HallType type;
    private Map<Long, String> availabilityStatus = new HashMap<>();
}