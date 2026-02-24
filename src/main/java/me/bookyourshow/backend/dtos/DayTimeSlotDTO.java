package me.bookyourshow.backend.dtos;

import lombok.*;
import me.bookyourshow.backend.enums.DaysEnum;

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
