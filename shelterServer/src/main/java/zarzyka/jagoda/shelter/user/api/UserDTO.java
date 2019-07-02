package zarzyka.jagoda.shelter.user.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zarzyka.jagoda.shelter.address.api.AddressDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class UserDTO {
    @NotEmpty
    private String login;
    private String name;
    private String surname;
    @NotNull
    private AddressDTO address;
    private String shelterUuid;

}

