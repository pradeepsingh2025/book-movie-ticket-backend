package me.bookyourshow.backend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.Duration;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieDTO {
    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Release year is required")
    private Integer releaseYear;

    private String description;

    @NotNull(message = "Duration is required")
    private Duration duration;

    private Double rating;

    @NotNull(message = "Movie poster image is required")
    private String posterImage;

    private String trailerURL;

    private List<Long> genres;

}
