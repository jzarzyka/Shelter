package zarzyka.jagoda.shelter.adoption.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zarzyka.jagoda.shelter.adoption.api.AdoptDTO;
import zarzyka.jagoda.shelter.adoption.api.AdoptedDTO;
import zarzyka.jagoda.shelter.adoption.mapper.AdoptedMapper;
import zarzyka.jagoda.shelter.adoption.mapper.ReceiverMapper;
import zarzyka.jagoda.shelter.adoption.model.AdoptionEntity;
import zarzyka.jagoda.shelter.adoption.repository.AdoptionRepository;
import zarzyka.jagoda.shelter.animal.model.AnimalEntity;
import zarzyka.jagoda.shelter.animal.repository.AnimalRepository;
import zarzyka.jagoda.shelter.shelter.model.ShelterEntity;
import zarzyka.jagoda.shelter.shelter.repository.ShelterRepository;
import zarzyka.jagoda.shelter.user.enums.Roles;
import zarzyka.jagoda.shelter.user.model.UserEntity;
import zarzyka.jagoda.shelter.user.service.AuthorizationService;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdoptionService {

    private final AuthorizationService authorizationService;
    private final AnimalRepository animalRepository;
    private final AdoptionRepository adoptionRepository;
    private final ShelterRepository shelterRepository;

    public AdoptedDTO adopt(String sessionId, AdoptDTO adoptDTO) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.SHELTER_WORKER) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be shelter worker");
        }

        AnimalEntity animalEntity = animalRepository.findByUuid(adoptDTO.getAnimalUuid())
                .orElseThrow(() -> new RuntimeException("Animal with uuid " + adoptDTO.getAnimalUuid() + " not found"));

        ShelterEntity shelterEntity = shelterRepository.findByUuid(userEntity.getShelterUuid())
                .orElseThrow(() -> new RuntimeException("Shelter for user with uuid " + userEntity.getUuid() + " not found"));

        AdoptionEntity adoptionEntity = AdoptionEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .date(new Date())
                .receiver(new ReceiverMapper().fromDto(adoptDTO.getReceiver()))
                .description(adoptDTO.getDescription())
                .animal(animalEntity)
                .build();

        animalEntity.setAdoptionUuid(adoptionEntity.getUuid());

        shelterEntity.setAnimalVacancy(shelterEntity.getAnimalVacancy() + 1);
        shelterRepository.save(shelterEntity);

        animalRepository.save(animalEntity);
        adoptionRepository.save(adoptionEntity);

        return new AdoptedMapper().toDto(adoptionEntity);
    }

    public AdoptedDTO getOne(String sessionId, String uuid) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.SHELTER_WORKER) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be shelter worker");
        }

        AdoptionEntity adoptionEntity = adoptionRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Adoption with uuid " + uuid + " not found"));

        return new AdoptedMapper().toDto(adoptionEntity);
    }
}
