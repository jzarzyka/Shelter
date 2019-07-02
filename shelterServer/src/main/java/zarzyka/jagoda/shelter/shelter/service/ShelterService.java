package zarzyka.jagoda.shelter.shelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zarzyka.jagoda.shelter.address.mapper.AddressMapper;
import zarzyka.jagoda.shelter.shelter.api.ShelterDTO;
import zarzyka.jagoda.shelter.shelter.mapper.ShelterMapper;
import zarzyka.jagoda.shelter.shelter.model.ShelterEntity;
import zarzyka.jagoda.shelter.shelter.repository.ShelterRepository;
import zarzyka.jagoda.shelter.user.api.UserDTO;
import zarzyka.jagoda.shelter.user.enums.Roles;
import zarzyka.jagoda.shelter.user.mapper.UserMapper;
import zarzyka.jagoda.shelter.user.model.UserEntity;
import zarzyka.jagoda.shelter.user.repository.UserRepository;
import zarzyka.jagoda.shelter.user.service.AuthorizationService;
import zarzyka.jagoda.shelter.user.service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;
    private final UserService userService;

    public ShelterDTO save(ShelterDTO shelterDTO, String sessionId) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.ADMIN) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be admin");
        }

        ShelterEntity shelterEntity = ShelterEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name(shelterDTO.getName())
                .address(new AddressMapper().fromDto(shelterDTO.getAddress()))
                .animalVacancy(shelterDTO.getAnimalVacancy())
                .build();

        shelterRepository.save(shelterEntity);
        return new ShelterMapper().toDto(shelterEntity);
    }


    public ShelterDTO update(ShelterDTO shelterDTO, String sessionId) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.ADMIN) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be admin");
        }

        ShelterEntity shelterEntity = shelterRepository.findByUuid(shelterDTO.getUuid())
                .orElseThrow(() -> new RuntimeException("Shelter with uuid " + shelterDTO.getUuid() + " not found"));

        shelterEntity.setName(shelterDTO.getName());
        shelterEntity.setAnimalVacancy(shelterDTO.getAnimalVacancy());
        shelterEntity.setAddress(new AddressMapper().fromDto(shelterDTO.getAddress()));

        shelterRepository.save(shelterEntity);
        return new ShelterMapper().toDto(shelterEntity);
    }

    public String delete(String uuid, String sessionId) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.ADMIN) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be admin");
        }

        ShelterEntity shelterEntity = shelterRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Shelter with uuid " + uuid + " not found"));

        List<UserEntity> shelterUsers = userRepository.findByShelter(shelterEntity.getUuid());
        for (UserEntity shelterUser : shelterUsers) {
            shelterUser.setShelterUuid("");
            userService.update(new UserMapper().toDto(shelterUser), sessionId);
        }

        shelterRepository.delete(shelterEntity);
        return shelterEntity.getName();
    }


    public ShelterDTO getOne(String uuid, String sessionId) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.ADMIN && userEntity.getRole() != Roles.SHELTER_WORKER) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be admin or shelter worker");
        }

        ShelterEntity shelterEntity = shelterRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Shelter with uuid " + uuid + " not found"));

        return new ShelterMapper().toDto(shelterEntity);
    }

    public List<ShelterDTO> getAll(String sessionId) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.ADMIN) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be admin");
        }

        ShelterMapper shelterMapper = new ShelterMapper();

        return shelterRepository.findAll()
                .stream()
                .map(shelterMapper::toDto)
                .collect(Collectors.toList());

    }

    public List<UserDTO> getAllShelterUsers(String shelterUuid, String sessionId) {
        UserEntity userEntityAuth = authorizationService.authorize(sessionId);
        if (userEntityAuth.getRole() != Roles.ADMIN) {
            throw new RuntimeException("User " + userEntityAuth.getLogin() + " must be admin");
        }

        List<UserEntity> results = userRepository.findByShelter(shelterUuid);

        UserMapper userMapper = new UserMapper();
        return results.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

}
