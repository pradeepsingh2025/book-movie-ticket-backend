package me.riazulislam.infinitecineplexbackend.mappers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateMovieDTO;
import me.riazulislam.infinitecineplexbackend.dtos.GenreDTO;
import me.riazulislam.infinitecineplexbackend.dtos.MovieDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateMovieDTO;
import me.riazulislam.infinitecineplexbackend.models.Genre;
import me.riazulislam.infinitecineplexbackend.models.Movie;
import me.riazulislam.infinitecineplexbackend.services.GenreService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class MovieMapper {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    public Movie toModel(CreateMovieDTO dto) {
        if (dto == null) {
            return null;
        }
        List<Long> genreIds = dto.getGenres();

        List<Genre> genres = new ArrayList<>();

        if (genreIds != null) {
            for (Long genreId : genreIds) {
                Genre genre = genreService.getGenreById(genreId);
                genres.add(genre);
            }
        }

        return Movie.builder()
                .title(dto.getTitle())
                .releaseYear(dto.getReleaseYear())
                .description(dto.getDescription())
                .rating(dto.getRating())
                .duration(dto.getDuration())
                .posterImage(dto.getPosterImage())
                .genres(genres)
                .build();
    }

    public MovieDTO toDTO(Movie movie) {
        if (movie == null) {
            return null;
        }

        List<GenreDTO> genreDTOList = new ArrayList<>();
        List<Genre> genresList = movie.getGenres();

        if (genresList != null) {
            for (Genre genre : genresList) {
                if (genre != null) {
                    genreDTOList.add(genreMapper.toDTO(genre));
                }
            }
        }

        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .releaseYear(movie.getReleaseYear())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .rating(movie.getRating())
                .posterImage(movie.getPosterImage())
                .genres(genreDTOList)
                .build();
    }

    public void updateModel(Movie existing, UpdateMovieDTO dto) {
        if (dto.getTitle() != null) {
            existing.setTitle(dto.getTitle());
        }
        if (dto.getReleaseYear() != null) {
            existing.setReleaseYear(dto.getReleaseYear());
        }
        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }
        if (dto.getDuration() != null) {
            existing.setDuration(dto.getDuration());
        }
        if (dto.getRating() != null) {
            existing.setRating(dto.getRating());
        }
        if (dto.getPosterImage() != null) {
            existing.setPosterImage(dto.getPosterImage());
        }
        if (dto.getGenres() != null) {
            List<Genre> genres = new ArrayList<>();
            for (Long genreId : dto.getGenres()) {
                Genre genre = genreService.getGenreById(genreId);
                genres.add(genre);
            }
            existing.setGenres(genres);
        }
    }
}
