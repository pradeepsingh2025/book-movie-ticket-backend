package me.bookyourshow.backend.repositories;

import me.bookyourshow.backend.models.ShowTime;
import me.bookyourshow.backend.enums.DaysEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    List<ShowTime> findByMovieId(Long movieId);

    List<ShowTime> findByHallId(Long hallId);

    List<ShowTime> findByShowDate(LocalDate showDate);

    List<ShowTime> findByMovieIdAndShowDate(Long movieId, LocalDate showDate);

    List<ShowTime> findByDay(DaysEnum day);

    List<ShowTime> findByShowDateAndStartTimeBetween(
            LocalDate showDate,
            LocalTime startTime,
            LocalTime endTime
    );

    Optional<ShowTime> findByIdAndShowDate(Long id, LocalDate showDate);

    /**
     * Find shows in the same hall on a given date that overlap the provided time range.
     * (existing.startTime < newEnd AND existing.endTime > newStart)
     */
    List<ShowTime> findByHallIdAndShowDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Long hallId,
            LocalDate showDate,
            LocalTime endTime,
            LocalTime startTime
    );

    /**
     * 🔑 NEW — Find hall IDs that are busy (have overlapping shows)
     * Used to determine available halls for a given date + time interval.
     */
    @Query("""
        SELECT DISTINCT s.hall.id
        FROM ShowTime s
        WHERE s.showDate = :date
          AND s.startTime < :endTime
          AND s.endTime   > :startTime
    """)
    List<Long> findBusyHallIds(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
