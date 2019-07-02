package zarzyka.jagoda.shelter.adoption.api;

import lombok.*;
import zarzyka.jagoda.shelter.animal.api.AnimalDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptedDTO {
    private String uuid;
    @PastOrPresent
    private Date date;
    @NotNull
    private ReceiverDTO receiver;
    @NotEmpty
    private String description;
    @NotNull
    private AnimalDTO animal;
}
