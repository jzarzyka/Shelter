package zarzyka.jagoda.shelter.adoption.api;

import lombok.*;
import zarzyka.jagoda.shelter.address.api.AddressDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverDTO {
    @Pattern(regexp = "[A-Z]{1}[a-z]", message = "Wrong name format")
    private String name;
    @Pattern(regexp = "[A-Z]{1}[a-zA-]*-?[a-z]*", message = "Wrong formatting of surname")
    private String surname;
    @NotEmpty
    private String phoneNumber;
    // https://emailregex.com/
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "Wrong e-mail format")
    private String email;
    @NotEmpty
    private AddressDTO address;
}
