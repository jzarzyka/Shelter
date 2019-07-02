package zarzyka.jagoda.shelter.animal.model;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@Document(collection = "animal")
public class AnimalEntity {

    @Id
    private String id;
    private String uuid;
    private String name;
    private String breed;
    private int age;
    private String description;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinProperty(name = "supervisorUuid")
    private String supervisorUuid;
    private String adoptionUuid;
}
