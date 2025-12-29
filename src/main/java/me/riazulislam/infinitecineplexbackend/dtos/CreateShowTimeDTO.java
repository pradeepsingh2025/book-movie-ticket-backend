package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.ShowStatusEnum;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateShowTimeDTO {
    private ShowStatusEnum status;
    private Long movie_id;
    private Long day_time_slot;
    private LocalDate show_date;
}
