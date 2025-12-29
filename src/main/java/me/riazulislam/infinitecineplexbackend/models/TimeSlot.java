package me.riazulislam.infinitecineplexbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "time_slots")
public class TimeSlot extends BaseModel{
    @Column(nullable = false)
    private String name;

    @Column(name = "start_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @Column(nullable = false)
    private Duration duration;

    @OneToMany(mappedBy = "timeSlot", orphanRemoval = false)
    @JsonBackReference
    private List<DayTimeSlot> dayTimeSlots = new ArrayList<>();
}
