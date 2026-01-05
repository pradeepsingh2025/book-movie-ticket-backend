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

import java.util.List;
import java.util.stream.Collectors;

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