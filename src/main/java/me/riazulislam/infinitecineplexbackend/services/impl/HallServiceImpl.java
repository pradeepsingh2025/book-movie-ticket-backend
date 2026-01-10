package me.riazulislam.infinitecineplexbackend.services.impl;

import lombok.RequiredArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateHallDTO;
import me.riazulislam.infinitecineplexbackend.dtos.HallDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateHallDTO;
import me.riazulislam.infinitecineplexbackend.mappers.HallMapper;
import me.riazulislam.infinitecineplexbackend.models.Hall;
import me.riazulislam.infinitecineplexbackend.repositories.HallRepository;
import me.riazulislam.infinitecineplexbackend.services.HallService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
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
        System.out.println("getting halls---------------------------------------------||||-----------------------");
        return hallRepository.findAll().stream()
                .map(hallMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HallDTO> getAvailableHalls(LocalDate requestedDate, LocalTime requestedStartTime, LocalTime requestedEndTime) {
        List<Hall> allHalls = hallRepository.findAll();
        
        LocalDateTime requestedStart = LocalDateTime.of(requestedDate, requestedStartTime);
        LocalDateTime requestedEnd = LocalDateTime.of(requestedDate, requestedEndTime);
        
        return allHalls.stream()
                .filter(hall -> isHallAvailable(hall, requestedStart, requestedEnd))
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

    
    
    private boolean isHallAvailable(Hall hall, LocalDateTime requestedStart, LocalDateTime requestedEnd) {
        Map<Long, String> availabilityStatus = hall.getAvailabilityStatus();
        
        // If no shows scheduled, hall is available
        if (availabilityStatus == null || availabilityStatus.isEmpty()) {
            return true;
        }
        
        // Check if requested date-time conflicts with any existing show
        for (String dateTimeFrame : availabilityStatus.values()) {
            if (hasDateTimeConflict(dateTimeFrame, requestedStart, requestedEnd)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean hasDateTimeConflict(String existingDateTimeFrame, LocalDateTime requestedStart, LocalDateTime requestedEnd) {
        try {
            // Parse existing date-time frame: "YYYY-MM-DD HH:mm:ss - HH:mm:ss"
            String[] parts = existingDateTimeFrame.split(" ");
            if (parts.length < 4) {
                return false;
            }
            
            String date = parts[0]; // YYYY-MM-DD
            String startTime = parts[1]; // HH:mm:ss
            String endTime = parts[3]; // HH:mm:ss (skip "-" at index 2)
            
            LocalDateTime existingStart = LocalDateTime.parse(date + "T" + startTime);
            LocalDateTime existingEnd = LocalDateTime.parse(date + "T" + endTime);
            
            // Check for overlap: two date-time ranges overlap if one starts before the other ends
            // Conflict exists if: (requestedStart < existingEnd) AND (requestedEnd > existingStart)
            return requestedStart.isBefore(existingEnd) && requestedEnd.isAfter(existingStart);
            
        } catch (Exception e) {
            // If parsing fails, assume no conflict
            return false;
        }
    }
}