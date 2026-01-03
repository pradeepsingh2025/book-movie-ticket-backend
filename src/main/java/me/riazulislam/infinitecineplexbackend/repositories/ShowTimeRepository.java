package me.riazulislam.infinitecineplexbackend.repositories;

import me.riazulislam.infinitecineplexbackend.models.DayTimeSlot;
import me.riazulislam.infinitecineplexbackend.models.Movie;
import me.riazulislam.infinitecineplexbackend.models.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    boolean existsShowTimeByMovieAndDayTimeSlot(Movie movie, DayTimeSlot dayTimeSlot);

    boolean existsShowTimeByMovieAndDayTimeSlotAndShowDate(Movie movie, DayTimeSlot dayTimeSlot, LocalDate showDate);

    List<ShowTime> findByMovieId(Long movieId);
}
