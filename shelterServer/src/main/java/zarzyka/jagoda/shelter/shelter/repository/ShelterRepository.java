package zarzyka.jagoda.shelter.shelter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import zarzyka.jagoda.shelter.shelter.model.ShelterEntity;

import java.util.Optional;

public interface ShelterRepository extends MongoRepository<ShelterEntity, String>, CustomShelterRepository {
    Optional<ShelterEntity> findByUuid(String uuid);
}
