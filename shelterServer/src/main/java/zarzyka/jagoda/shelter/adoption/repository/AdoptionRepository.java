package zarzyka.jagoda.shelter.adoption.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import zarzyka.jagoda.shelter.adoption.model.AdoptionEntity;

import java.util.Optional;

public interface AdoptionRepository extends MongoRepository<AdoptionEntity, String> {
    Optional<AdoptionEntity> findByUuid(String uuid);
}
