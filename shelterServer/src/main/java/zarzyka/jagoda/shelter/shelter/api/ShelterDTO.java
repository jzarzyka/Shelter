package zarzyka.jagoda.shelter.shelter.api;

import lombok.*;
import zarzyka.jagoda.shelter.address.api.AddressDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterDTO {
    private String uuid;
    @NotEmpty
    private String name;
    @NotNull
    private AddressDTO address;
    private int animalVacancy;
}
