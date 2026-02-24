package me.bookyourshow.backend.services;

import me.bookyourshow.backend.dtos.UpdateMovieDTO;
import me.bookyourshow.backend.models.Movie;

import java.util.List;

public interface MovieService {
    Movie createNewMovie(Movie movie);
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    Movie updateMovie(Long id, UpdateMovieDTO movieDTO);
    void deleteMovie(Long id);
}
