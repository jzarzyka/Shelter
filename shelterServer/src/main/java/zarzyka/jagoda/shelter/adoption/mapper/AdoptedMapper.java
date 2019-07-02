package zarzyka.jagoda.shelter.adoption.mapper;

import zarzyka.jagoda.shelter.adoption.api.AdoptedDTO;
import zarzyka.jagoda.shelter.adoption.model.AdoptionEntity;
import zarzyka.jagoda.shelter.animal.mapper.AnimalMapper;

public class AdoptedMapper {
    public AdoptedDTO toDto(AdoptionEntity adoptionEntity) {
        return AdoptedDTO.builder()
                .uuid(adoptionEntity.getUuid())
                .date(adoptionEntity.getDate())
                .description(adoptionEntity.getDescription())
                .animal(new AnimalMapper().toDto(adoptionEntity.getAnimal()))
                .receiver(new ReceiverMapper().toDto(adoptionEntity.getReceiver()))
                .build();
    }
}
