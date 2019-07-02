package zarzyka.jagoda.shelter.adoption.api;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptDTO {
    private String animalUuid;
    @NotEmpty
    private ReceiverDTO receiver;
    @NotEmpty
    private String description;
}
