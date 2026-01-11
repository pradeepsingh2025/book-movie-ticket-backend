package me.riazulislam.infinitecineplexbackend.repositories;

import me.riazulislam.infinitecineplexbackend.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    // add query methods as needed
}
