package zarzyka.jagoda.shelter.adoption;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zarzyka.jagoda.shelter.adoption.api.AdoptDTO;
import zarzyka.jagoda.shelter.adoption.api.AdoptedDTO;
import zarzyka.jagoda.shelter.adoption.service.AdoptionService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adoption")
public class AdoptionController {

    private final AdoptionService adoptionService;

    @PostMapping(value = "")
    public ResponseEntity<?> adoptAnimal(@Valid @RequestParam String sessionId, @RequestBody AdoptDTO adoptDTO) {
        AdoptedDTO adoptedDTO = adoptionService.adopt(sessionId, adoptDTO);
        return ResponseEntity.ok(adoptedDTO);
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<?> getAdoption(@Valid @RequestParam String sessionId, @PathVariable String uuid) {
        AdoptedDTO adoptedDTO = adoptionService.getOne(sessionId, uuid);
        return ResponseEntity.ok(adoptedDTO);
    }

}
