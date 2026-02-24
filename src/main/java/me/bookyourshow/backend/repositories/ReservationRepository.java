package me.bookyourshow.backend.repositories;

import me.bookyourshow.backend.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByShowTime_IdAndSeats_Id(Long id, Long id1);
    List<Reservation> findByUser_Id(Long userId);
}
