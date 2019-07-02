package zarzyka.jagoda.shelter.shelter.repository;

import zarzyka.jagoda.shelter.shelter.model.ShelterEntity;
import zarzyka.jagoda.shelter.user.model.UserEntity;

import java.util.Optional;

public interface CustomShelterRepository {
    Optional<ShelterEntity> findByUser(UserEntity userEntity);
}

