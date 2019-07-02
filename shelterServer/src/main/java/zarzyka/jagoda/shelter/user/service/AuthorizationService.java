package zarzyka.jagoda.shelter.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zarzyka.jagoda.shelter.user.model.UserEntity;
import zarzyka.jagoda.shelter.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserRepository userRepository;

    public UserEntity authorize(String sessionId) {
        Optional<UserEntity> userEntityOptional = userRepository.findBySessionId(sessionId);
        return userEntityOptional.orElseThrow(() -> new RuntimeException("Wrong sessionId: " + sessionId));
    }
}