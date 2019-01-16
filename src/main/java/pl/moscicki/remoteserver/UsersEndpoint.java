package pl.moscicki.remoteserver;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
class UsersEndpoint {

  private final UserService userService;

  public UsersEndpoint(UserService userService) {
    this.userService = userService;
  }

  @CrossOrigin
  @PostMapping
  void add(@RequestBody @Validated UserDto user) {
    userService.addUser(user);
  }

  @CrossOrigin
  @GetMapping("/all")
  Set<UserDto> getUsers(){
    return userService.getUsers();
  }

  @CrossOrigin
  @PostMapping("/exists")
  UserExistsDto checkUserExistance(@RequestBody @Validated String username) {
    return userService.userExists(username);
  }

  @CrossOrigin
  @DeleteMapping
  void deleteUser(@RequestBody @Validated String username) {
    userService.deleteUser(username);
  }
}
