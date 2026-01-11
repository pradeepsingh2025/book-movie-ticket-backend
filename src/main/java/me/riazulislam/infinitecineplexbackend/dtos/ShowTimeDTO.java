package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import me.riazulislam.infinitecineplexbackend.enums.DaysEnum;

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
    private List<Long> reservationIds;
}
