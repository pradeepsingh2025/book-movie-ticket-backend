package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.SeatStatusEnum;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private String seat;
    private SeatStatusEnum status;
}
