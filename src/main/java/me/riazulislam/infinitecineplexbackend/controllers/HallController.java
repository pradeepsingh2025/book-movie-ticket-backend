package me.riazulislam.infinitecineplexbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.riazulislam.infinitecineplexbackend.dtos.CreateHallDTO ;
import me.riazulislam.infinitecineplexbackend.dtos.HallDTO ;
import me.riazulislam.infinitecineplexbackend.dtos.UpdateHallDTO ;
import me.riazulislam.infinitecineplexbackend.services.HallService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
@RequiredArgsConstructor
public class HallController {
    
    private final HallService hallService;
    
    @PostMapping
    public ResponseEntity<HallDTO> createHall(@Valid @RequestBody CreateHallDTO createHallDto) {
        HallDTO  createdHall = hallService.createHall(createHallDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHall);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HallDTO> getHallById(@PathVariable Long id) {
        HallDTO  hallDto = hallService.getHallById(id);
        return ResponseEntity.ok(hallDto);
    }
    
    @GetMapping
    public ResponseEntity<List<HallDTO>> getAllHalls() {
        System.out.println("hall controller for getting all halls---------------|||||||||||||||||||----------------------------------");
        List<HallDTO> halls = hallService.getAllHalls();
        return ResponseEntity.ok(halls);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HallDTO> updateHall(
            @PathVariable Long id,
            @Valid @RequestBody UpdateHallDTO  updateHallDto) {
        HallDTO updatedHall = hallService.updateHall(id, updateHallDto);
        return ResponseEntity.ok(updatedHall);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
        return ResponseEntity.noContent().build();
    }
}