package zarzyka.jagoda.shelter.shelter.mapper;

import zarzyka.jagoda.shelter.address.mapper.AddressMapper;
import zarzyka.jagoda.shelter.shelter.api.ShelterDTO;
import zarzyka.jagoda.shelter.shelter.model.ShelterEntity;

public class ShelterMapper {

    public ShelterDTO toDto(ShelterEntity shelterEntity) {
        return ShelterDTO.builder()
                .uuid(shelterEntity.getUuid())
                .name(shelterEntity.getName())
                .animalVacancy(shelterEntity.getAnimalVacancy())
                .address(new AddressMapper().toDto(shelterEntity.getAddress()))
                .build();
    }

    public ShelterEntity fromDto(ShelterDTO shelterDTO) {
        return ShelterEntity.builder()
                .name(shelterDTO.getName())
                .animalVacancy(shelterDTO.getAnimalVacancy())
                .address(new AddressMapper().fromDto(shelterDTO.getAddress()))
                .build();
    }
}
