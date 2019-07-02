package zarzyka.jagoda.shelter.animal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zarzyka.jagoda.shelter.animal.api.AnimalDTO;
import zarzyka.jagoda.shelter.animal.mapper.AnimalMapper;
import zarzyka.jagoda.shelter.animal.model.AnimalEntity;
import zarzyka.jagoda.shelter.animal.repository.AnimalRepository;
import zarzyka.jagoda.shelter.shelter.model.ShelterEntity;
import zarzyka.jagoda.shelter.shelter.repository.ShelterRepository;
import zarzyka.jagoda.shelter.user.enums.Roles;
import zarzyka.jagoda.shelter.user.model.UserEntity;
import zarzyka.jagoda.shelter.user.service.AuthorizationService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AuthorizationService authorizationService;
    private final AnimalRepository animalRepository;
    private final ShelterRepository shelterRepository;

    public AnimalDTO save(String sessionId, AnimalDTO animalDTO) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.SHELTER_WORKER) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be shelter worker");
        }

        ShelterEntity shelterEntity = shelterRepository.findByUuid(userEntity.getShelterUuid())
                .orElseThrow(() -> new RuntimeException("Shelter for user with uuid " + userEntity.getUuid() + " not found"));

        AnimalEntity animalEntity = AnimalEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name(animalDTO.getName())
                .age(animalDTO.getAge())
                .breed(animalDTO.getBreed())
                .supervisorUuid(userEntity.getUuid())
                .description(animalDTO.getDescription())
                .build();

        shelterEntity.setAnimalVacancy(shelterEntity.getAnimalVacancy() - 1);
        shelterEntity.getAnimals().add(animalEntity);
        shelterRepository.save(shelterEntity);

        animalRepository.save(animalEntity);
        return new AnimalMapper().toDto(animalEntity);
    }

    public AnimalDTO update(String sessionId, AnimalDTO animalDTO) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.SHELTER_WORKER) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be shelter worker");
        }

        ShelterEntity shelterEntity = shelterRepository.findByUuid(userEntity.getShelterUuid())
                .orElseThrow(() -> new RuntimeException("Shelter for user with uuid " + userEntity.getUuid() + " not found"));

        AnimalEntity animalEntity = animalRepository.findByUuid(animalDTO.getUuid())
                .orElseThrow(() -> new RuntimeException("Animal with uuid " + animalDTO.getUuid() + " not found"));

        animalEntity.setAge(animalDTO.getAge());
        animalEntity.setBreed(animalDTO.getBreed());
        animalEntity.setDescription(animalDTO.getDescription());
        animalEntity.setSupervisorUuid(animalDTO.getSupervisorUuid());
        animalEntity.setName(animalDTO.getName());

        shelterEntity.getAnimals().add(animalEntity);
        shelterRepository.save(shelterEntity);

        animalRepository.save(animalEntity);

        return new AnimalMapper().toDto(animalEntity);
    }

    public List<AnimalDTO> getAll(String sessionId) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.SHELTER_WORKER) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be shelter worker");
        }

        ShelterEntity shelterEntity = shelterRepository.findByUuid(userEntity.getShelterUuid())
                .orElseThrow(() -> new RuntimeException("Shelter for user with uuid " + userEntity.getUuid() + " not found"));

        AnimalMapper animalMapper = new AnimalMapper();
        return shelterEntity.getAnimals().stream().map(animalMapper::toDto).collect(Collectors.toList());
    }

    public AnimalDTO getOne(String sessionId, String uuid) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.SHELTER_WORKER) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be shelter worker");
        }

        AnimalEntity animalEntity = animalRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Animal with uuid " + uuid + " not found"));

        return new AnimalMapper().toDto(animalEntity);
    }

}
