package me.riazulislam.infinitecineplexbackend.controllers;

import lombok.AllArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateShowTimeDTO;
import me.riazulislam.infinitecineplexbackend.dtos.ShowTimeDTO;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateShowTimeDTO;
import me.riazulislam.infinitecineplexbackend.mappers.ShowTimeMapper;
import me.riazulislam.infinitecineplexbackend.models.ShowTime;
import me.riazulislam.infinitecineplexbackend.services.ShowTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/show-times")
@AllArgsConstructor
public class ShowTimeController {
    private final ShowTimeService showTimeService;
    private final ShowTimeMapper showTimeMapper;

    @PostMapping
    public ResponseEntity<?> createNewShowTime(@RequestBody CreateShowTimeDTO showTimeDTO) {
        ShowTime createdNewShowTime = showTimeService.createNewShowTime(showTimeMapper.toModel(showTimeDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(showTimeMapper.toDTO(createdNewShowTime));
    }

    @GetMapping
    public ResponseEntity<List<ShowTimeDTO>> getAllShowTimes() {
        List<ShowTime> showTimes = showTimeService.getAllShowTimes();
        List<ShowTimeDTO> showTimeDTOs = showTimes.stream()
                .map(showTimeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(showTimeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowTimeDTO> getShowTimeById(@PathVariable("id") Long id) {
        ShowTime showTime = showTimeService.getShowTimeById(id);
        return ResponseEntity.ok(showTimeMapper.toDTO(showTime));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowTimeDTO> updateShowTime(@PathVariable Long id, @RequestBody UpdateShowTimeDTO showTimeDTO) {
        ShowTime updatedShowTime = showTimeService.updateShowTime(id, showTimeDTO);
        return ResponseEntity.ok(showTimeMapper.toDTO(updatedShowTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowTime(@PathVariable Long id) {
        showTimeService.deleteShowTime(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowTimeDTO>> getShowTimesByMovieId(@PathVariable("movieId") Long movieId) {
        List<ShowTime> showTimes = showTimeService.getShowTimesByMovieId(movieId);
        List<ShowTimeDTO> showTimeDTOs = showTimes.stream()
                .map(showTimeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(showTimeDTOs);
    }
}
