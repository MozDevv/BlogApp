package app.moz.blogapp.rest;

import app.moz.blogapp.Entity.User;
import app.moz.blogapp.jwt.JWTUtil;
import app.moz.blogapp.payloads.PostDTO;
import app.moz.blogapp.payloads.UserDto;
import app.moz.blogapp.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class UserController {

    UserService userService;

    private final JWTUtil jwtUtil;

    public UserController(UserService theUserService, JWTUtil jwtUtil1) {
        userService = theUserService;
        jwtUtil = jwtUtil1;
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {

        return userService.getAllUsers();

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable int userId) {

        UserDto theUser = userService.getUserById(userId);

        return new ResponseEntity<UserDto>(theUser, HttpStatus.OK);

    }


    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {

        UserDto userDto1 = userService.createUser(userDto);

        String token = jwtUtil.issueToken(userDto.getEmail(), "ROLE_USER");

        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(userDto);

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable int id) {
        UserDto theUser = userService.updateUser(userDto, id);

        return new ResponseEntity<>(theUser, HttpStatus.OK);

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PostMapping("/userid")
    public Integer getUserIdByEmail(@RequestBody String email) {
        return userService.getUserIdbyEmail(email);
    }


}
