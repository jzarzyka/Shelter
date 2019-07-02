package zarzyka.jagoda.shelter.user.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zarzyka.jagoda.shelter.address.api.AddressDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
    @NotEmpty
    private String login;
    @NotEmpty
    @Size(min = 8, max = 100, message = "Size of password should be between 8-100 characters")
    private String password;
    @NotEmpty
    @Pattern(regexp = "[A-Z]{1}[a-zA-Z]*", message = "Wrong name format")
    private String name;
    @Pattern(regexp = "[A-Z]{1}[a-zA-Z]*-?[a-z]*", message = "Wrong surname format")
    private String surname;
    private AddressDTO address;
    private String shelterUuid;
}