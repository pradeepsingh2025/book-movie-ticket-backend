package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.ShowStatusEnum;

import java.time.LocalDate;
import java.time.LocalTime;
import java.math.BigDecimal;
import me.riazulislam.infinitecineplexbackend.enums.DaysEnum;

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
