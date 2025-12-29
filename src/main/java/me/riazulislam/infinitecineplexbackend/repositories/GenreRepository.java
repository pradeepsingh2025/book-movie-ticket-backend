package me.riazulislam.infinitecineplexbackend.repositories;

import me.riazulislam.infinitecineplexbackend.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
