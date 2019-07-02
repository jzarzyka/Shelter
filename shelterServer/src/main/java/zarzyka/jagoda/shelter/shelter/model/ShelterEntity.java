package zarzyka.jagoda.shelter.shelter.model;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToMany;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import zarzyka.jagoda.shelter.address.model.AddressEntity;
import zarzyka.jagoda.shelter.animal.model.AnimalEntity;
import zarzyka.jagoda.shelter.user.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shelter")
public class ShelterEntity {

    @Id
    private String id;
    private String uuid;
    private String name;
    private AddressEntity address;
    private int animalVacancy;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinProperty(name = "users")
    private List<UserEntity> users = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinProperty(name = "animals")
    private List<AnimalEntity> animals = new ArrayList<>();

    public List<UserEntity> getUsers() {
        return users;
    }

    public List<AnimalEntity> getAnimals() {
        return animals;
    }
}
