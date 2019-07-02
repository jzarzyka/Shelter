package zarzyka.jagoda.shelter.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import zarzyka.jagoda.shelter.address.model.AddressEntity;
import zarzyka.jagoda.shelter.user.enums.Roles;

@Setter
@Getter
@Builder
@ToString
@Document(collection = "user")
public class UserEntity {

    @Id
    private String id;
    private String uuid;
    private String login;
    private String password;
    private String sessionId;
    private String name;
    private String surname;
    private AddressEntity address;
    private String shelterUuid;
    private Roles role;
}