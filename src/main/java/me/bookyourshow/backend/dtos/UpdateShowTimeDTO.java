package me.bookyourshow.backend.dtos;

import lombok.*;
import me.bookyourshow.backend.enums.ShowStatusEnum;

import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;
import me.bookyourshow.backend.enums.DaysEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateShowTimeDTO {
    private ShowStatusEnum status;
    private Long movieId;
    private DaysEnum day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long hallId;
    private LocalDate showDate;
    private BigDecimal price;
}
