package zarzyka.jagoda.shelter.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import zarzyka.jagoda.shelter.user.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String>, CustomUserRepository {
    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findBySessionId(String sessionId);

    boolean existsByLogin(String login);
}
