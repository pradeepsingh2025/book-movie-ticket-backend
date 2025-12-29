package me.riazulislam.infinitecineplexbackend.controllers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateMovieDTO;
import me.riazulislam.infinitecineplexbackend.dtos.MovieDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateMovieDTO;
import me.riazulislam.infinitecineplexbackend.mappers.MovieMapper;
import me.riazulislam.infinitecineplexbackend.models.Movie;
import me.riazulislam.infinitecineplexbackend.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody CreateMovieDTO movieDTO) {
        Movie createdMovie = movieService.createNewMovie(movieMapper.toModel(movieDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(movieMapper.toDTO(createdMovie));
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        List<MovieDTO> movieDTOs = movies.stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movieMapper.toDTO(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody UpdateMovieDTO movieDTO) {
        Movie updatedMovie = movieService.updateMovie(id, movieDTO);
        return ResponseEntity.ok(movieMapper.toDTO(updatedMovie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
