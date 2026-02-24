package me.riazulislam.infinitecineplexbackend.services;

import me.riazulislam.infinitecineplexbackend.dtos.UpdateMovieDTO;
import me.riazulislam.infinitecineplexbackend.models.Movie;

import java.util.List;

public interface MovieService {
    Movie createNewMovie(Movie movie);
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    Movie updateMovie(Long id, UpdateMovieDTO movieDTO);
    void deleteMovie(Long id);
}
