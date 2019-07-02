package zarzyka.jagoda.shelter.shelter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zarzyka.jagoda.shelter.shelter.api.ShelterDTO;
import zarzyka.jagoda.shelter.shelter.service.ShelterService;
import zarzyka.jagoda.shelter.user.api.UserDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shelter")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterService shelterService;

    @PostMapping(value = "")
    public ResponseEntity<?> save(@Valid @RequestBody ShelterDTO shelterDTO, @RequestParam String sessionId) {
        ShelterDTO savedShelter = shelterService.save(shelterDTO, sessionId);
        return ResponseEntity.ok(savedShelter);
    }

    @PutMapping(value = "")
    public ResponseEntity<?> update(@Valid @RequestBody ShelterDTO shelterDTO, @RequestParam String sessionId) {
        ShelterDTO updatedShelter = shelterService.update(shelterDTO, sessionId);
        return ResponseEntity.ok(updatedShelter);
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<?> delete(@Valid @PathVariable String uuid, @RequestParam String sessionId) {
        shelterService.delete(uuid, sessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll(@Valid @RequestParam String sessionId) {
        List<ShelterDTO> shelters = shelterService.getAll(sessionId);
        return ResponseEntity.ok(shelters);
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<?> getOne(@Valid @PathVariable String uuid, @RequestParam String sessionId) {
        ShelterDTO shelterDTO = shelterService.getOne(uuid, sessionId);
        return ResponseEntity.ok(shelterDTO);
    }

    @GetMapping(value = "/users/{uuid}")
    public ResponseEntity<?> getAllShelterUsers(@Valid @PathVariable String uuid, @Valid @RequestParam String sessionId) {
        List<UserDTO> shelterUsers = shelterService.getAllShelterUsers(uuid, sessionId);
        return ResponseEntity.ok(shelterUsers);
    }

}
