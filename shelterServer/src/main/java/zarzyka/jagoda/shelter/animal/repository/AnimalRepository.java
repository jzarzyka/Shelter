package zarzyka.jagoda.shelter.animal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import zarzyka.jagoda.shelter.animal.model.AnimalEntity;

import java.util.Optional;

public interface AnimalRepository extends MongoRepository<AnimalEntity, String> {

    Optional<AnimalEntity> findByUuid(String uuid);

}
