package me.riazulislam.infinitecineplexbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import me.riazulislam.infinitecineplexbackend.enums.DaysEnum;
import me.riazulislam.infinitecineplexbackend.enums.ShowStatusEnum;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

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
    @JoinColumn(name = "hall_id", nullable = false)
    @JsonBackReference
    private Hall hall;

     // Explicit times (important for overlap detection)
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "show_date", nullable = false)
    private LocalDate showDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DaysEnum day;

    @OneToMany(mappedBy = "showTime")
    @JsonBackReference
    private List<Reservation> reservations;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
