package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.SeatStatusEnum;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSeatDTO {
    // Only status is required for status updates
    private SeatStatusEnum status;
}
