package zarzyka.jagoda.shelter.user.repository;

import zarzyka.jagoda.shelter.user.model.UserEntity;

import java.util.List;

public interface CustomUserRepository {
    List<UserEntity> findByShelter(String shelterUuid);

}
