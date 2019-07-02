package zarzyka.jagoda.shelter.address.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    private String street;
    private String houseNumber;
    private String zipCode;
    private String city;
    private String country;
}
