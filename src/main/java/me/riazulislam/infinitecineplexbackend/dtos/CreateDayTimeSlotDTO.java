package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.DaysEnum;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDayTimeSlotDTO {
    private DaysEnum day;
    private Long time_slot;
}
