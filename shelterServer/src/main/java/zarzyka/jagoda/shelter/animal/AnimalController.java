package zarzyka.jagoda.shelter.animal;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zarzyka.jagoda.shelter.animal.api.AnimalDTO;
import zarzyka.jagoda.shelter.animal.service.AnimalService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping(value = "")
    public ResponseEntity<?> save(@Valid @RequestParam String sessionId, @RequestBody AnimalDTO animalDTO) {
        AnimalDTO savedAnimal = animalService.save(sessionId, animalDTO);
        return ResponseEntity.ok(savedAnimal);
    }

    @PutMapping(value = "")
    public ResponseEntity<?> update(@Valid @RequestParam String sessionId, @RequestBody AnimalDTO animalDTO) {
        AnimalDTO updatedAnimal = animalService.update(sessionId, animalDTO);
        return ResponseEntity.ok(updatedAnimal);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAll(@Valid @RequestParam String sessionId) {
        List<AnimalDTO> shelterAnimals = animalService.getAll(sessionId);
        return ResponseEntity.ok(shelterAnimals);
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<?> getOne(@Valid @RequestParam String sessionId, @PathVariable String uuid) {
        AnimalDTO animalDTO = animalService.getOne(sessionId, uuid);
        return ResponseEntity.ok(animalDTO);
    }
}
