package springframework.usersapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springframework.usersapp.payload.UserDto;
import springframework.usersapp.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/POST/users")
    public ResponseEntity<UserDto> createUser (@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PatchMapping("/PATCH/users/{id}")
    public ResponseEntity<UserDto> updateSomeUserFileds(@PathVariable(name = "id") long id, @RequestBody Map<String, Object> fields){
        return new ResponseEntity<>(userService.updateSomeUserFields(id, fields), HttpStatus.OK);
    }

    @PutMapping("/PUT/users/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable(name="id") long id){
        return new ResponseEntity<>(userService.updateUser(userDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/DELETE/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name="id") long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfull", HttpStatus.OK);
    }

    @GetMapping("/GET/users/{fromDate}to{toDate}")
    public ResponseEntity<List<UserDto>> findUserByBirthdateRange(@PathVariable(name = "fromDate") Date fromDate, @PathVariable(name = "toDate") Date toDate){
        return new ResponseEntity<>(userService.findUserByBirthdateRange(fromDate, toDate), HttpStatus.OK);
    }
}
