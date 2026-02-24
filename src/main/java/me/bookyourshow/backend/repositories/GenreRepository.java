package me.bookyourshow.backend.repositories;

import me.bookyourshow.backend.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
