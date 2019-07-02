package zarzyka.jagoda.shelter.user.mapper;

import zarzyka.jagoda.shelter.address.mapper.AddressMapper;
import zarzyka.jagoda.shelter.user.api.UserDTO;
import zarzyka.jagoda.shelter.user.model.UserEntity;

public class UserMapper {
    public UserDTO toDto(UserEntity userEntity) {
        return UserDTO.builder()
                .login(userEntity.getLogin())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .address(new AddressMapper().toDto(userEntity.getAddress()))
                .shelterUuid(userEntity.getShelterUuid())
                .build();
    }

    public UserEntity fromDto(UserDTO userDTO) {
        return UserEntity.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .address(new AddressMapper().fromDto(userDTO.getAddress()))
                .shelterUuid(userDTO.getShelterUuid())
                .build();
    }
}