package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.time.Duration;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDTO {
    private Long id;
    private String name;
    private LocalTime startTime;
    private Duration duration;
}
