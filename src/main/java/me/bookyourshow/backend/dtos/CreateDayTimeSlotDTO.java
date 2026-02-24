package me.bookyourshow.backend.dtos;

import lombok.*;
import me.bookyourshow.backend.enums.DaysEnum;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDayTimeSlotDTO {
    private DaysEnum day;
    private Long time_slot;
}
