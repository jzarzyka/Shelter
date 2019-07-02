package zarzyka.jagoda.shelter.animal.mapper;

import zarzyka.jagoda.shelter.animal.api.AnimalDTO;
import zarzyka.jagoda.shelter.animal.model.AnimalEntity;

public class AnimalMapper {
    public AnimalDTO toDto(AnimalEntity animalEntity) {
        return AnimalDTO.builder()
                .uuid(animalEntity.getUuid())
                .name(animalEntity.getName())
                .age(animalEntity.getAge())
                .description(animalEntity.getDescription())
                .breed(animalEntity.getBreed())
                .supervisorUuid(animalEntity.getSupervisorUuid())
                .adoptionUuid(animalEntity.getAdoptionUuid())
                .build();
    }
}
