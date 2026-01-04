package me.riazulislam.infinitecineplexbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.ShowStatusEnum;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "show_times")
public class ShowTime extends BaseModel {
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ShowStatusEnum showStatus;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonBackReference
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    @JsonBackReference
    private DayTimeSlot dayTimeSlot;

    @Column(name = "show_date", nullable = false)
    private LocalDate showDate;

    @OneToMany(mappedBy = "showTime")
    @JsonBackReference
    private List<Reservation> reservations;

}
