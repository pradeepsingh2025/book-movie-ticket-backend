package me.bookyourshow.backend.repositories;

import me.bookyourshow.backend.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    // add query methods as needed
    boolean existsSeatsBySeat(String seat);

    boolean existsSeatsById(Long id);
}
