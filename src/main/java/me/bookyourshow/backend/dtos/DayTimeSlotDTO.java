package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.DaysEnum;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayTimeSlotDTO {
    private Long id;
    private DaysEnum day;
    private TimeSlotDTO timeSlot;
}
