package me.riazulislam.infinitecineplexbackend.dtos;

import lombok.*;

import java.time.Duration;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private Integer releaseYear;
    private String description;
    private Duration duration;
    private Double rating;
    private String posterImage;
    private List<GenreDTO> genres;
    private List<ShowTimeDTO> showTimes;
}
