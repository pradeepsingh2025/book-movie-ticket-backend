package me.riazulislam.infinitecineplexbackend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movies")
public class Movie extends BaseModel {
    @Column(nullable = false)
    private String title;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    private String description;

    @Column(nullable = false)
    private Duration duration;

    private Double rating;

    @Column(name = "poster_image")
    private String posterImage;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonManagedReference
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private List<ShowTime> showTimes = new ArrayList<>();
}
