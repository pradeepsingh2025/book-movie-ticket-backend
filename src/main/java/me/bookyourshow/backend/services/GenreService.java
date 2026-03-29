package me.bookyourshow.backend.services;

import me.bookyourshow.backend.models.Genre;

import java.util.List;

public interface GenreService {
    Genre getGenreById(Long id);
    Genre createGenre(Genre genre);
    List<Genre> getAllGenres();
}
