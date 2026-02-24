package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDTO {
    private Long id;
    private String name;
    
    @JsonProperty("start_time")
    private LocalTime startTime;
}
