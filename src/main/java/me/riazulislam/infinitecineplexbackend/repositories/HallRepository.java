package me.riazulislam.infinitecineplexbackend.repositories;

import me.riazulislam.infinitecineplexbackend.models.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
}