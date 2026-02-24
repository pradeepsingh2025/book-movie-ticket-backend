package me.bookyourshow.backend.services.impl;

import lombok.RequiredArgsConstructor;
import me.bookyourshow.backend.dtos.CreateHallDTO;
import me.bookyourshow.backend.dtos.HallDTO;
import me.bookyourshow.backend.dtos.UpdateHallDTO;
import me.bookyourshow.backend.mappers.HallMapper;
import me.bookyourshow.backend.models.Hall;
import me.bookyourshow.backend.repositories.HallRepository;
import me.bookyourshow.backend.repositories.ShowTimeRepository;
import me.bookyourshow.backend.services.HallService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final ShowTimeRepository showTimeRepository;
    private final HallMapper hallMapper;

    @Override
    @Transactional
    public HallDTO createHall(CreateHallDTO createHallDto) {
        Hall hall = hallMapper.toEntity(createHallDto);
        Hall savedHall = hallRepository.save(hall);
        return hallMapper.toDto(savedHall);
    }

    @Override
    @Transactional(readOnly = true)
    public HallDTO getHallById(Long id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + id));
        return hallMapper.toDto(hall);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HallDTO> getAllHalls() {
        return hallRepository.findAll()
                .stream()
                .map(hallMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HallDTO> getAvailableHalls(
            LocalDate requestedDate,
            LocalTime requestedStartTime,
            LocalTime requestedEndTime
    ) {

        // 1️⃣ Find halls that already have overlapping shows
        List<Long> busyHallIds =
                showTimeRepository.findBusyHallIds(
                        requestedDate,
                        requestedStartTime,
                        requestedEndTime
                );

        // 2️⃣ If no halls are busy → all halls are available
        List<Hall> availableHalls = busyHallIds.isEmpty()
                ? hallRepository.findAll()
                : hallRepository.findByIdNotIn(busyHallIds);

        // 3️⃣ Map to DTOs
        return availableHalls.stream()
                .map(hallMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HallDTO updateHall(Long id, UpdateHallDTO updateHallDto) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hall not found with id: " + id));
        hallMapper.updateEntityFromDto(updateHallDto, hall);
        Hall updatedHall = hallRepository.save(hall);
        return hallMapper.toDto(updatedHall);
    }

    @Override
    @Transactional
    public void deleteHall(Long id) {
        if (!hallRepository.existsById(id)) {
            throw new RuntimeException("Hall not found with id: " + id);
        }
        hallRepository.deleteById(id);
    }
}
