package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.SeatStatusEnum;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSeatDTO {
    private String seat;
    private SeatStatusEnum status;
    // Associations represented by their IDs for creation
    private Long showTimeId;
    private Long hallId;
}

