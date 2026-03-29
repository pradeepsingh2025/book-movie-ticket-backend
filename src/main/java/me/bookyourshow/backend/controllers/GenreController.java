package me.bookyourshow.backend.controllers;

import lombok.AllArgsConstructor;
import me.bookyourshow.backend.dtos.CreateGenreDTO;
import me.bookyourshow.backend.mappers.GenreMapper;
import me.bookyourshow.backend.models.Genre;
import me.bookyourshow.backend.services.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @PostMapping
    public Genre createGenre(@RequestBody CreateGenreDTO genreDTO) {
        return genreService.createGenre(genreMapper.toEntity(genreDTO));
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
}
