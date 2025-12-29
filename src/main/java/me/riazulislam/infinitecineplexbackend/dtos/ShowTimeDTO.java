package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowTimeDTO {
    private Long id;
    private MovieDTO movie;
    private String status;
    private DayTimeSlotDTO timeSlot;
    private LocalDate showDate;
    private List<Long> reservationIds;
}
