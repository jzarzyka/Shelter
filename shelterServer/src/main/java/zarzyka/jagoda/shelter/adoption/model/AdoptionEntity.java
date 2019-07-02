package zarzyka.jagoda.shelter.adoption.model;


import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import zarzyka.jagoda.shelter.animal.model.AnimalEntity;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "adoption")
public class AdoptionEntity {

    @Id
    private String id;
    private String uuid;
    private Date date;
    private ReceiverEntity receiver;
    private String description;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinProperty(name = "animal")
    private AnimalEntity animal;
}
