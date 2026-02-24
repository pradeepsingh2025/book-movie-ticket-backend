package me.riazulislam.infinitecineplexbackend.services;

import me.riazulislam.infinitecineplexbackend.models.Genre;

public interface GenreService {
    Genre getGenreById(Long id);
    Genre createGenre(Genre genre);
}
