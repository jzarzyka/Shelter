package zarzyka.jagoda.shelter.user.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zarzyka.jagoda.shelter.user.enums.Roles;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class LoggedUserDTO {
    @NotEmpty
    private String sessionId;
    private Roles role;
}
