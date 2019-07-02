package zarzyka.jagoda.shelter.animal.api;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    private String uuid;
    @NotEmpty
    private String name;
    @NotEmpty
    private String breed;
    @PositiveOrZero
    private int age;
    private String description;
    @NotEmpty
    private String supervisorUuid;
    private String adoptionUuid;
}