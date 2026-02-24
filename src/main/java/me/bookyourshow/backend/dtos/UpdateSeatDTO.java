package me.bookyourshow.backend.dtos;

import lombok.*;
import me.bookyourshow.backend.enums.SeatStatusEnum;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSeatDTO {
    // Only status is required for status updates
    private SeatStatusEnum status;
}
