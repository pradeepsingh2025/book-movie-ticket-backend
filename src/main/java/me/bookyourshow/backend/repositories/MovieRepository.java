package me.bookyourshow.backend.repositories;

import me.bookyourshow.backend.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsMovieByTitle(String movieTitle);
}
