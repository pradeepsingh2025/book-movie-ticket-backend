package me.riazulislam.infinitecineplexbackend.controllers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateGenreDTO;
import me.riazulislam.infinitecineplexbackend.mappers.GenreMapper;
import me.riazulislam.infinitecineplexbackend.models.Genre;
import me.riazulislam.infinitecineplexbackend.services.GenreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
