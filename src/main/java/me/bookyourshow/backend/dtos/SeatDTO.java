package me.bookyourshow.backend.dtos;

import lombok.*;
import me.bookyourshow.backend.enums.SeatStatusEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDTO {
    private Long id;
    private String seat;
    private SeatStatusEnum status;
    private Long showTimeId;
    private Long hallId;
}

