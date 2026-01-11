package me.riazulislam.infinitecineplexbackend.models;

import jakarta.persistence.*;
import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.SeatStatusEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "seats")
public class Seat extends BaseModel {

    @Column(nullable = false)
    private String seat; // e.g., "A1", "B3"

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private ShowTime showTime;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatusEnum status;

}
