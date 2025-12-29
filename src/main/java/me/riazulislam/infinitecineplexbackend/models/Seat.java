package me.riazulislam.infinitecineplexbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.SeatStatusEnum;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "seats")
public class Seat extends BaseModel {
    @Column(nullable = false)
    private String seat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'Active'")
    private SeatStatusEnum status;

    @ManyToMany(mappedBy = "seats")
    @JsonBackReference
    private List<Reservation> reservations;
}
