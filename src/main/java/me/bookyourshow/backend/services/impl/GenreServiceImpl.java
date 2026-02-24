package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.models.Genre;
import me.riazulislam.infinitecineplexbackend.repositories.GenreRepository;
import me.riazulislam.infinitecineplexbackend.services.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Genre getGenreById(Long id) {
        try {
            if (genreRepository.existsById(id)) {
                return genreRepository.getOne(id);
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching Genre details");
        }
    }

    @Override
    public Genre createGenre(Genre genre) {
        try {
            return genreRepository.save(genre);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
