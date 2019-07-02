package zarzyka.jagoda.shelter.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zarzyka.jagoda.shelter.user.api.LoggedUserDTO;
import zarzyka.jagoda.shelter.user.api.LoginUserDTO;
import zarzyka.jagoda.shelter.user.api.RegisterUserDTO;
import zarzyka.jagoda.shelter.user.api.UserDTO;
import zarzyka.jagoda.shelter.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDTO loginUserDTO) {
        LoggedUserDTO loggedUserDTO = userService.login(loginUserDTO);
        return ResponseEntity.ok(loggedUserDTO);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDTO registerUserDTO) {
        LoggedUserDTO loggedUserDTO = userService.register(registerUserDTO);
        return ResponseEntity.ok(loggedUserDTO);
    }

    @PutMapping(value = "")
    public ResponseEntity<?> update(@Valid @RequestBody UserDTO userDTO, @RequestParam String sessionId) {
        UserDTO updatedUser = userService.update(userDTO, sessionId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> details(@Valid @RequestParam String sessionId) {
        UserDTO userDTO = userService.getDetails(sessionId);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll(@Valid @RequestParam String sessionId) {
        List<UserDTO> users = userService.getAll(sessionId);
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<?> getOne(@Valid @PathVariable String uuid, @RequestParam String sessionId) {
        UserDTO shelterDTO = userService.getOne(uuid, sessionId);
        return ResponseEntity.ok(shelterDTO);
    }

    @DeleteMapping(value = "/{login}")
    public ResponseEntity<?> delete(@Valid @PathVariable String login, @RequestParam String sessionId) {
        userService.delete(login, sessionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
