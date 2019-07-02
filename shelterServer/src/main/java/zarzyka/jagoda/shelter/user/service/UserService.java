package zarzyka.jagoda.shelter.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import zarzyka.jagoda.shelter.address.mapper.AddressMapper;
import zarzyka.jagoda.shelter.shelter.model.ShelterEntity;
import zarzyka.jagoda.shelter.shelter.repository.ShelterRepository;
import zarzyka.jagoda.shelter.user.api.LoggedUserDTO;
import zarzyka.jagoda.shelter.user.api.LoginUserDTO;
import zarzyka.jagoda.shelter.user.api.RegisterUserDTO;
import zarzyka.jagoda.shelter.user.api.UserDTO;
import zarzyka.jagoda.shelter.user.enums.Roles;
import zarzyka.jagoda.shelter.user.mapper.UserMapper;
import zarzyka.jagoda.shelter.user.model.UserEntity;
import zarzyka.jagoda.shelter.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final AuthorizationService authorizationService;
    private final ShelterRepository shelterRepository;

    public LoggedUserDTO login(LoginUserDTO loginUserDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findByLogin(loginUserDTO.getLogin());
        UserEntity userEntity = userEntityOptional
                .orElseThrow(() -> new RuntimeException("Wrong login or password for user: " + loginUserDTO.getLogin()));

        if (bCryptPasswordEncoder.matches(loginUserDTO.getPassword(), userEntity.getPassword())) {
            userEntity.setSessionId(generateSessionId());
            userRepository.save(userEntity);
            return LoggedUserDTO.builder()
                    .sessionId(userEntity.getSessionId())
                    .role(userEntity.getRole())
                    .build();
        } else {
            throw new RuntimeException("Wrong login or password for user: " + loginUserDTO.getLogin());
        }
    }

    public LoggedUserDTO register(RegisterUserDTO registerUserDTO) {
        if (userRepository.existsByLogin(registerUserDTO.getLogin())) {
            throw new RuntimeException("User with login " + registerUserDTO.getLogin() + " already exists");
        }

        ShelterEntity shelterEntity = shelterRepository.findByUuid(registerUserDTO.getShelterUuid())
                .orElseThrow(() -> new RuntimeException("No shelter with id " + registerUserDTO.getShelterUuid() + " found"));

        UserEntity userEntity = UserEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .login(registerUserDTO.getLogin())
                .password(bCryptPasswordEncoder.encode(registerUserDTO.getPassword()))
                .sessionId(generateSessionId())
                .name(registerUserDTO.getName())
                .surname(registerUserDTO.getSurname())
                .address(new AddressMapper().fromDto(registerUserDTO.getAddress()))
                .shelterUuid(registerUserDTO.getShelterUuid())
                .role(Roles.SHELTER_WORKER)
                .build();


        shelterEntity.getUsers().add(userEntity);
        shelterRepository.save(shelterEntity);

        userRepository.save(userEntity);
        return LoggedUserDTO.builder()
                .sessionId(userEntity.getSessionId())
                .build();
    }

    public UserDTO update(UserDTO userDTO, String sessionId) {
        UserEntity userEntityAuth = authorizationService.authorize(sessionId);
        if (userEntityAuth.getRole() != Roles.ADMIN) {
            throw new RuntimeException("User " + userEntityAuth.getLogin() + " must be admin");
        }

        UserEntity userEntity = userRepository.findByLogin(userDTO.getLogin())
                .orElseThrow(() -> new RuntimeException("Shelter with uuid " + userDTO.getLogin() + " not found"));

        userEntity.setName(userDTO.getName());
        userEntity.setSurname(userDTO.getSurname());
        userEntity.setShelterUuid(userDTO.getShelterUuid());
        userEntity.setAddress(new AddressMapper().fromDto(userDTO.getAddress()));

        userRepository.save(userEntity);
        return new UserMapper().toDto(userEntity);
    }

    private String generateSessionId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "123456789";
        String alphaNumeric = characters + characters.toLowerCase() + numbers;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            result.append(alphaNumeric.charAt((int) (Math.random() * alphaNumeric.length())));
        }
        return result.toString();
    }

    public UserDTO getDetails(String sessionId) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        return new UserMapper().toDto(userEntity);
    }

    public List<UserDTO> getAll(String sessionId) {
        UserEntity userEntity = authorizationService.authorize(sessionId);
        if (userEntity.getRole() != Roles.ADMIN) {
            throw new RuntimeException("User " + userEntity.getLogin() + " must be admin");
        }

        UserMapper userMapper = new UserMapper();
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO getOne(String login, String sessionId) {
        UserEntity userEntityAuth = authorizationService.authorize(sessionId);
        if (userEntityAuth.getRole() != Roles.ADMIN && userEntityAuth.getRole() != Roles.SHELTER_WORKER) {
            throw new RuntimeException("User " + userEntityAuth.getLogin() + " must be admin or shelter worker");
        }

        UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User with login " + login + " not found"));

        return new UserMapper().toDto(userEntity);
    }

    public String delete(String login, String sessionId) {
        UserEntity userEntityAuth = authorizationService.authorize(sessionId);
        if (userEntityAuth.getRole() != Roles.ADMIN) {
            throw new RuntimeException("User " + userEntityAuth.getLogin() + " must be admin");
        }

        UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User with login " + login + " not found"));

        userRepository.delete(userEntity);
        return userEntity.getName();
    }
}
