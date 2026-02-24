package me.bookyourshow.backend.mappers;

import me.bookyourshow.backend.dtos.CreateGenreDTO;
import me.bookyourshow.backend.dtos.GenreDTO;
import me.bookyourshow.backend.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public Genre toEntity(CreateGenreDTO genreDTO) {
        return Genre.builder()
                .name(genreDTO.getName())
                .build();
    }

    public GenreDTO toDTO(Genre genre) {
        return GenreDTO.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
