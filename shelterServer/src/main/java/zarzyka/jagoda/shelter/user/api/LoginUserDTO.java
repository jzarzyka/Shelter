package zarzyka.jagoda.shelter.user.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginUserDTO {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
