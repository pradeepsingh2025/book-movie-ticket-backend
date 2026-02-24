package me.bookyourshow.backend.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.math.BigDecimal;
import me.bookyourshow.backend.enums.DaysEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowTimeDTO {
    private Long id;
    private MovieDTO movie;
    private String status;
    private DaysEnum day;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate showDate;
    private Long hallId;
    private HallDTO hall;
    private List<Long> reservationIds;
    private BigDecimal price;
}
