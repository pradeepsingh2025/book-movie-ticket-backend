package me.bookyourshow.backend.services;

import me.bookyourshow.backend.models.Genre;

public interface GenreService {
    Genre getGenreById(Long id);
    Genre createGenre(Genre genre);
}
