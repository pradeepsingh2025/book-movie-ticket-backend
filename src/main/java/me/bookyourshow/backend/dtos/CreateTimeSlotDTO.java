package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTimeSlotDTO {
    private String name;
    private LocalTime start_time;
}
