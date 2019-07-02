package zarzyka.jagoda.shelter.adoption.model;

import lombok.*;
import zarzyka.jagoda.shelter.address.model.AddressEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverEntity {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private AddressEntity address;
}
