package zarzyka.jagoda.shelter.address.api;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String street;
    @PositiveOrZero
    @NotEmpty
    private String houseNumber;
    @NotEmpty
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}")
    private String zipCode;
    @NotEmpty
    private String city;
    @NotEmpty
    private String country;
}
