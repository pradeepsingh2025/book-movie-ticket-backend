package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateMovieDTO;
import me.riazulislam.infinitecineplexbackend.mappers.MovieMapper;
import me.riazulislam.infinitecineplexbackend.models.Movie;
import me.riazulislam.infinitecineplexbackend.repositories.MovieRepository;
import me.riazulislam.infinitecineplexbackend.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public Movie createNewMovie(Movie movie) {
        try {
            if (movieRepository.existsMovieByTitle(movie.getTitle())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Movie already exists");
            }

            return movieRepository.save(movie);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public List<Movie> getAllMovies() {
        try {
            return movieRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Movie getMovieById(Long id) {
        try {
            return movieRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Movie updateMovie(Long id, UpdateMovieDTO movieDTO) {
        try {
            Movie existingMovie = getMovieById(id);
            movieMapper.updateModel(existingMovie, movieDTO);
            return movieRepository.save(existingMovie);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteMovie(Long id) {
        try {
            if (!movieRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
            }
            movieRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
